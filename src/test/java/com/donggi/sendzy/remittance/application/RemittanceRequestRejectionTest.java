package com.donggi.sendzy.remittance.application;

import com.donggi.sendzy.account.domain.TestAccountRepository;
import com.donggi.sendzy.member.TestUtils;
import com.donggi.sendzy.member.application.SignupService;
import com.donggi.sendzy.member.domain.MemberService;
import com.donggi.sendzy.member.domain.TestMemberRepository;
import com.donggi.sendzy.member.dto.SignupRequest;
import com.donggi.sendzy.remittance.domain.RemittanceRequest;
import com.donggi.sendzy.remittance.domain.RemittanceRequestStatus;
import com.donggi.sendzy.remittance.domain.repository.TestRemittanceRequestRepository;
import com.donggi.sendzy.remittance.domain.service.RemittanceRequestService;
import com.donggi.sendzy.remittance.exception.InvalidRemittanceRequestStatusException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RemittanceRequestRejectionTest {

    @Autowired
    private TestMemberRepository memberRepository;

    @Autowired
    private TestAccountRepository accountRepository;

    @Autowired
    private TestRemittanceRequestRepository remittanceRequestRepository;

    @Autowired
    private SignupService signupService;

    @Autowired
    private RemittanceRequestService remittanceRequestService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private RemittanceRequestProcessor remittanceRequestProcessor;

    private Long requestId;
    private Long receiverId;

    @BeforeEach
    void setUp() {
        remittanceRequestRepository.deleteAll();
        accountRepository.deleteAll();
        memberRepository.deleteAll();

        final var senderEmail = "sender@sendzy.com";
        final var receiverEmail = "receiver@sendzy.com";
        signupService.signup(new SignupRequest(senderEmail, TestUtils.DEFAULT_RAW_PASSWORD));
        signupService.signup(new SignupRequest(receiverEmail, TestUtils.DEFAULT_RAW_PASSWORD));

        final var sender = memberService.findByEmail(senderEmail).get();
        final var receiver = memberService.findByEmail(receiverEmail).get();

        receiverId = receiver.getId();

        requestId = remittanceRequestService.recordRequestAndGetId(
            new RemittanceRequest(
                sender.getId(),
                receiverId,
                RemittanceRequestStatus.PENDING,
                1000L
            )
        );
    }

    @AfterEach
    void tearDown() {
        remittanceRequestRepository.deleteAll();
        accountRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Nested
    class 송금_요청_거절_시 {

        @Nested
        class 수신자가_송금_요청을_거절하면 {
            @Test
            void 상태가_REJECTED로_변경된다() {
                // when
                remittanceRequestProcessor.handleRejection(requestId, receiverId);

                // then
                final var request = remittanceRequestService.getById(requestId);
                assertThat(request.getStatus()).isEqualTo(RemittanceRequestStatus.REJECTED);
            }
        }

        @Nested
        class 수신자가_아닌_회원이_거절하면 {
            @Test
            void AccessDeniedException_예외가_발생한다() {
                // given: 수신자가 아닌 사용자 추가
                final var otherEmail = "intruder@sendzy.com";
                signupService.signup(new SignupRequest(otherEmail, TestUtils.DEFAULT_RAW_PASSWORD));
                final var otherMember = memberService.findByEmail(otherEmail).get();

                // when
                final var exception = assertThrows(AccessDeniedException.class, () ->
                    remittanceRequestProcessor.handleRejection(requestId, otherMember.getId())
                );

                // then
                assertThat(exception.getMessage()).isEqualTo("해당 송금 요청의 수신자만 처리할 수 있습니다.");
            }
        }

        @Nested
        class 이미_처리된_요청은 {
            @Test
            void InvalidRemittanceRequestStatusException_예외가_반환된다() {
                // given
                final var request = remittanceRequestService.getById(requestId);
                remittanceRequestService.accept(request);

                // when
                final var actual = assertThrows(InvalidRemittanceRequestStatusException.class, () ->
                    remittanceRequestProcessor.handleRejection(requestId, receiverId)
                );

                // then
                assertThat(actual.getMessage()).isEqualTo("이미 처리된 송금 요청입니다. 현재 상태는 '" + request.getStatus() +  "'입니다.");
            }
        }
    }
}
