package com.donggi.sendzy.remittance.application;

import com.donggi.sendzy.account.domain.AccountService;
import com.donggi.sendzy.account.domain.TestAccountRepository;
import com.donggi.sendzy.member.TestUtils;
import com.donggi.sendzy.member.application.SignupService;
import com.donggi.sendzy.member.domain.MemberService;
import com.donggi.sendzy.member.domain.TestMemberRepository;
import com.donggi.sendzy.member.dto.SignupRequest;
import com.donggi.sendzy.remittance.domain.RemittanceRequest;
import com.donggi.sendzy.remittance.domain.repository.TestRemittanceRequestRepository;
import com.donggi.sendzy.remittance.domain.repository.TestRemittanceStatusHistoryRepository;
import com.donggi.sendzy.remittance.domain.service.RemittanceRequestService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static com.donggi.sendzy.remittance.domain.RemittanceRequestStatus.EXPIRED;
import static com.donggi.sendzy.remittance.domain.RemittanceRequestStatus.PENDING;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RemittanceExpirationServiceTest {
    @Autowired
    private SignupService signupService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private RemittanceRequestService remittanceRequestService;
    @Autowired
    private RemittanceExpirationService remittanceExpirationService;

    @Autowired
    private TestMemberRepository memberRepository;
    @Autowired
    private TestAccountRepository accountRepository;
    @Autowired
    private TestRemittanceRequestRepository remittanceRequestRepository;
    @Autowired
    private TestRemittanceStatusHistoryRepository historyRepository;

    private Long senderId;
    private Long receiverId;

    @BeforeEach
    void setUp() {
        historyRepository.deleteAll();
        remittanceRequestRepository.deleteAll();
        accountRepository.deleteAll();
        memberRepository.deleteAll();

        signupService.signup(new SignupRequest("sender@test.com", TestUtils.DEFAULT_RAW_PASSWORD));
        signupService.signup(new SignupRequest("receiver@test.com", TestUtils.DEFAULT_RAW_PASSWORD));

        senderId = memberService.findByEmail("sender@test.com").get().getId();
        receiverId = memberService.findByEmail("receiver@test.com").get().getId();

        var senderAccount = accountService.getByMemberId(senderId);
        accountService.deposit(senderAccount, 5000L);
        accountService.withdraw(senderAccount, 1000L); // pending amount 설정
    }

    @AfterEach
    void tearDown() {
        historyRepository.deleteAll();
        remittanceRequestRepository.deleteAll();
        accountRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void 만료된_송금_요청은_EXPIRED로_변경되고_대기금액이_롤백된다() {
        // given
        var expiredCreatedAt = LocalDateTime.now().minusDays(4);
        var request = new RemittanceRequest(senderId, receiverId, PENDING, 1000L, expiredCreatedAt);
        remittanceRequestService.recordRequestAndGetId(request);

        // when
        remittanceExpirationService.expireRequestBatch(1000, LocalDateTime.now());

        // then
        var expired = remittanceRequestService.getById(request.getId());
        assertThat(expired.getStatus()).isEqualTo(EXPIRED);

        var senderAccount = accountService.getByMemberId(senderId);
        assertThat(senderAccount.getPendingAmount()).isEqualTo(0L);
        assertThat(senderAccount.getBalance()).isEqualTo(5000L);

        var histories = historyRepository.findAll();
        assertThat(histories).hasSize(1);
        assertThat(histories.get(0).getStatus()).isEqualTo(EXPIRED);
    }
}
