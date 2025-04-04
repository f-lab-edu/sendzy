package com.donggi.sendzy.remittance.application;

import com.donggi.sendzy.account.domain.AccountService;
import com.donggi.sendzy.member.TestUtils;
import com.donggi.sendzy.member.application.SignupService;
import com.donggi.sendzy.member.domain.MemberService;
import com.donggi.sendzy.member.domain.TestMemberRepository;
import com.donggi.sendzy.member.dto.SignupRequest;
import com.donggi.sendzy.remittance.domain.RemittanceRequest;
import com.donggi.sendzy.remittance.domain.RemittanceRequestStatus;
import com.donggi.sendzy.remittance.domain.repository.TestRemittanceRequestRepository;
import com.donggi.sendzy.remittance.domain.service.RemittanceRequestService;
import com.donggi.sendzy.remittance.exception.RemittanceRequestNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RemittanceResponseServiceTest {

    // 인증된 사용자의 송금 건의 상태가 PENDING이면서 만료되지 않은 경우, 송금 요청이 수락/거절 처리되고 송금 요청 상태 변경 이력이 기록되어야 함
    // 송금 건이 수락되면 송금자의 잔액이 차감되어야 하고, 거절되면 송금자의 잔액이 복구되어야 함
    @Autowired
    private RemittanceRequestService remittanceRequestService;

    @Autowired
    private TestRemittanceRequestRepository remittanceRequestRepository;

    @Autowired
    private SignupService signupService;

    @Autowired
    private TestMemberRepository memberRepository;

    private void doSomething(final long requestId, final RemittanceRequestStatus status) {
        remittanceRequestService.getByIdForUpdate(requestId);
    }

    private Long requestId;

    @BeforeEach
    void setUp() {
        remittanceRequestRepository.deleteAll();
        memberRepository.deleteAll();

        final var senderEmail = "sender@sendzy.com";
        final var receiverEmail = "receiver@sendzy.com";
        signupService.signup(new SignupRequest(senderEmail, TestUtils.DEFAULT_RAW_PASSWORD));
        signupService.signup(new SignupRequest(receiverEmail, TestUtils.DEFAULT_RAW_PASSWORD));

        requestId = remittanceRequestService.recordRequestAndGetId(
            new RemittanceRequest(
                1L,
                2L,
                RemittanceRequestStatus.PENDING,
                1000L
            )
        );
    }

    @Nested
    class 송금_요청_수락_시 {
        @Nested
        class 송금_요청이_존재하지_않을_경우 {
            @Test
            void RemittanceRequestNotFoundException_예외가_발생한다() {
                // given
                final long requestId = 999999999999L;

                // when
                final var exception = assertThrows(RemittanceRequestNotFoundException.class, () -> doSomething(requestId, RemittanceRequestStatus.ACCEPTED));

                // then
                assertThat(exception.getMessage()).isEqualTo("송금 요청 정보를 찾을 수 없습니다.");
            }
        }
    }
}
