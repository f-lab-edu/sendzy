package com.donggi.sendzy.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
public class MemberTest {

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 멤버_등록은 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 요청이_정상적이면 {

            @Test
            @DisplayName("멤버가 생성된다")
            void 멤버가_생성된다() {
                // given
                final var email = "donggi@sendzy.com";
                final var password = "superpassword";
                final var expected = new SignupRequest(email, password);

                // when
                final var actual = new Member(expected.getEmail(), expected.getPassword());

                // then
                assertThat(actual).isNotNull();
            }
        }
    }
}

class SignupRequest {

    private String email;
    private String password;

    public SignupRequest(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

class Member {

    private Long id;
    private String email;
    private String password;
    private LocalDateTime createdAt;

    public Member(final String email, final String password) {
        this.id = 1L;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }
}
