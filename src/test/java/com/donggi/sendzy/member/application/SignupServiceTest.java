package com.donggi.sendzy.member.application;

import com.donggi.sendzy.account.domain.AccountService;
import com.donggi.sendzy.member.domain.MemberService;
import com.donggi.sendzy.member.dto.SignupRequest;
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

    @Test
    void 비밀번호가_암호화된_상태로_저장된다() {
        // given
        final var request = new SignupRequest(DEFAULT_EMAIL, DEFAULT_RAW_PASSWORD);

        // when
        signupService.signup(request);

        // then
        var savedMember = memberService.findByEmailOrThrow(DEFAULT_EMAIL);
        var savedAccount = accountService.getByMemberId(savedMember.getId());
        assertThat(savedMember).isNotNull();
        assertThat(savedAccount).isNotNull();
    }
}
