package com.donggi.sendzy.remittance.application;

import com.donggi.sendzy.account.domain.AccountService;
import com.donggi.sendzy.account.domain.TestAccountRepository;
import com.donggi.sendzy.account.exception.AccountNotFoundException;
import com.donggi.sendzy.account.exception.InvalidWithdrawalException;
import com.donggi.sendzy.common.exception.BadRequestException;
import com.donggi.sendzy.member.TestUtils;
import com.donggi.sendzy.member.application.SignupService;
import com.donggi.sendzy.member.domain.MemberService;
import com.donggi.sendzy.member.domain.TestMemberRepository;
import com.donggi.sendzy.member.dto.SignupRequest;
import com.donggi.sendzy.remittance.domain.repository.TestRemittanceRequestRepository;
import com.donggi.sendzy.remittance.domain.service.RemittanceHistoryService;
import com.donggi.sendzy.remittance.domain.service.RemittanceRequestService;
import com.donggi.sendzy.remittance.domain.service.RemittanceStatusHistoryService;
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
class RemittanceRequestApplicationServiceTest {

    @Autowired
    private RemittanceRequestApplicationService remittanceRequestApplicationService;

    @Autowired
    private SignupService signupService;

    @Autowired
    private TestMemberRepository memberRepository;

    @Autowired
    private TestAccountRepository accountRepository;

    @Autowired
    private TestRemittanceRequestRepository remittanceRequestRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private RemittanceHistoryService remittanceHistoryService;

    @Autowired
    private RemittanceRequestService remittanceRequestService;

    @Autowired
    private RemittanceStatusHistoryService remittanceStatusHistoryService;

    private Long senderId;
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

        senderId = sender.getId();
        receiverId = receiver.getId();
    }

    @Nested
    class 송금_요청_시 {
        @Nested
        class 송금자가_존재하지_않으면 {
            @Test
            void AccountNotFoundException_예외가_발생한다() {
                // given
                final var invalidSenderId = -1L;

                // when, then
                assertThrows(AccountNotFoundException.class, () -> {
                    remittanceRequestApplicationService.sendMoney(invalidSenderId, receiverId, 100L);
                });
            }
        }

        @Nested
        class 수신자가_존재하지_않으면 {
            @Test
            void AccountNotFoundException_예외가_발생한다() {
                // given
                final var invalidReceiverId = -1L;

                // when, then
                assertThrows(AccountNotFoundException.class, () -> {
                    remittanceRequestApplicationService.sendMoney(senderId, invalidReceiverId, 100L);
                });
            }
        }

        @Nested
        class 송금액이_0보다_작으면 {
            @Test
            void InvalidWithdrawalException_예외가_발생한다() {
                // given
                final var invalidAmount = -1L;

                // when, then
                assertThrows(InvalidWithdrawalException.class, () -> {
                    remittanceRequestApplicationService.sendMoney(senderId, receiverId, invalidAmount);
                });
            }
        }

        @Nested
        class 송금액이_송금자의_잔액보다_크면 {
            @Test
            void InvalidWithdrawalException_예외가_발생한다() {
                // given
                final var senderAccount = accountService.getByMemberId(senderId);
                final var amount = 1000L;
                senderAccount.deposit(amount);

                // when & then
                assertThrows(InvalidWithdrawalException.class, () -> {
                    remittanceRequestApplicationService.sendMoney(senderId, receiverId, amount + 1);
                });
            }
        }

        @Nested
        class 송금자와_수신자가_같으면 {
            @Test
            void BadRequestException_예외가_발생한다() {
                // when, then
                assertThrows(BadRequestException.class, () -> {
                    remittanceRequestApplicationService.sendMoney(senderId, senderId, 100L);
                });
            }
        }

        @Nested
        class 송금_요청이_정상적으로_처리되면 {
            @Test
            void 송금_내역이_저장된다() {
                // given
                final var senderAccount = accountService.getByMemberId(senderId);
                final var amount = 1000L;
                accountService.deposit(senderAccount, amount);

                // when
                remittanceRequestApplicationService.sendMoney(senderId, receiverId, 100L);

                // then
                final var remittanceHistory = remittanceHistoryService.findBySenderId(senderId);
                assertThat(remittanceHistory).isNotNull();
            }

            @Test
            void 송금_요청이_저장된다() {
                // given
                final var senderAccount = accountService.getByMemberId(senderId);
                final var amount = 1000L;
                accountService.deposit(senderAccount, amount);

                // when
                remittanceRequestApplicationService.sendMoney(senderId, receiverId, 100L);

                // then
                final var remittanceRequest = remittanceRequestService.findBySenderId(senderId);
                assertThat(remittanceRequest).isNotNull();
            }

            @Test
            void PENDING_상태의_송금_상태_정보가_저장된다() {
                // given
                final var senderAccount = accountService.getByMemberId(senderId);
                final var amount = 1000L;
                accountService.deposit(senderAccount, amount);

                // when
                remittanceRequestApplicationService.sendMoney(senderId, receiverId, 100L);

                // then
                final var remittanceStatusHistory = remittanceStatusHistoryService.findBySenderId(senderId);
                assertThat(remittanceStatusHistory).isNotNull();
            }

            @Test
            void 송금액만큼_송금자의_송금_대기_금액이_증가한다() {
                // given
                final var senderAccount = accountService.getByMemberId(senderId);
                final var amount = 1000L;
                accountService.deposit(senderAccount, amount);

                // when
                remittanceRequestApplicationService.sendMoney(senderId, receiverId, 100L);

                // then
                final var actual = accountService.getByMemberId(senderId);
                assertThat(actual.getPendingAmount()).isEqualTo(100L);
            }
        }
    }
}
