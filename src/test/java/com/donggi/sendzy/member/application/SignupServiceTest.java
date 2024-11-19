package com.donggi.sendzy.member.application;

import com.donggi.sendzy.member.domain.MemberService;
import com.donggi.sendzy.member.dto.SignupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.donggi.sendzy.member.TestUtils.DEFAULT_EMAIL;
import static com.donggi.sendzy.member.TestUtils.DEFAULT_PASSWORD;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class SignupServiceTest {

    @Mock
    private MemberService memberService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private SignupService signupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 비밀번호가_암호화된_상태로_저장된다() {
        // given
        final var email = DEFAULT_EMAIL;
        final var rawPassword = DEFAULT_PASSWORD;
        final var encodedPassword = "encrypted_password";
        final var request = new SignupRequest(email, rawPassword);

        when(memberService.existsByEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        // when
        signupService.signup(request);

        // then
        verify(passwordEncoder).encode(rawPassword);
        verify(memberService).save(email, encodedPassword);
    }
}
