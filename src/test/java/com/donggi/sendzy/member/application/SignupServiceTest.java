package com.donggi.sendzy.member.application;

import com.donggi.sendzy.account.domain.AccountRepository;
import com.donggi.sendzy.account.domain.AccountService;
import com.donggi.sendzy.account.domain.TestAccountRepository;
import com.donggi.sendzy.member.domain.MemberService;
import com.donggi.sendzy.member.domain.TestMemberRepository;
import com.donggi.sendzy.member.dto.SignupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.donggi.sendzy.member.TestUtils.DEFAULT_EMAIL;
import static com.donggi.sendzy.member.TestUtils.DEFAULT_RAW_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignupServiceTest {

    @Autowired
    private SignupService signupService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TestMemberRepository memberRepository;

    @Autowired
    private TestAccountRepository testAccountRepository;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        testAccountRepository.deleteAll();
    }

    @Test
    void 비밀번호가_암호화된_상태로_저장된다() {
        // given
        final var request = new SignupRequest(DEFAULT_EMAIL, DEFAULT_RAW_PASSWORD);

        // when
        signupService.signup(request);

        // then
        var savedMember = memberService.findByEmail(request.email()).get();
        assertThat(savedMember).isNotNull();
    }

    @Test
    void 회원가입_요청이_정상적이면_회원의_계좌가_생성된다() {
        // given
        final var request = new SignupRequest(DEFAULT_EMAIL, DEFAULT_RAW_PASSWORD);

        // when
        signupService.signup(request);

        // then
        var savedMember = memberService.findByEmail(request.email()).get();
        final var savedAccount = accountService.getByMemberId(savedMember.getId());
        assertThat(savedAccount).isNotNull();
    }
}
