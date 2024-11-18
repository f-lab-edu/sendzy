package com.donggi.sendzy.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
public class MemberTest {

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 멤버_등록시 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 요청이_정상적이면 {

            @Test
            @DisplayName("멤버가 생성된다")
            void 멤버가_생성된다() {
                // given
                final var email = "donggi@sendzy.com";
                final var password = "SuperPa2ssword!@#";

                // when
                final var actual = new Member(email, password);

                // then
                assertThat(actual).isNotNull();
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 이메일이_포함되지_않으면 {

            @Test
            @DisplayName("예외가 발생한다")
            void 예외가_발생한다() {
                // given
                final var password = "superpassword";

                // when & then
                assertThatThrownBy(() -> new Member(null, password))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이메일은 필수입니다.");
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 이메일이_100자를_넘어가면 {

            @Test
            @DisplayName("예외가 발생한다")
            void 예외가_발생한다() {
                // given
                final var email = "a".repeat(101);
                final var password = "superpassword";

                // when & then
                assertThatThrownBy(() -> new Member(email, password))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이메일은 100자를 넘을 수 없습니다.");
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 이메일이_5자_미만이면 {

            @Test
            @DisplayName("예외가 발생한다")
            void 예외가_발생한다() {
                // given
                final var email = "a".repeat(4);
                final var password = "superpassword";

                // when & then
                assertThatThrownBy(() -> new Member(email, password))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이메일은 5자 이상이어야 합니다.");
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 이메일이_이메일_형식이_아니면 {

            @Test
            @DisplayName("예외가 발생한다")
            void 예외가_발생한다() {
                // given
                final var email = "donggi";
                final var password = "superpassword";

                // when & then
                assertThatThrownBy(() -> new Member(email, password))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이메일 형식이 아닙니다.");
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 비밀번호가_포함되지_않으면 {

            @Test
            @DisplayName("예외가 발생한다")
            void 예외가_발생한다() {
                // given
                final var email = "donggi@sendzy.com";

                // when & then
                assertThatThrownBy(() -> new Member(email, null))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("비밀번호는 필수입니다.");
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 비밀번호가_32자를_넘어가면 {

            @Test
            @DisplayName("예외가 발생한다")
            void 예외가_발생한다() {
                // given
                final var email = "donggi@sendzy.com";
                final var password = "a".repeat(33);

                // when & then
                assertThatThrownBy(() -> new Member(email, password))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("비밀번호는 32자를 넘을 수 없습니다.");
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 비밀번호가_8자_미만이면 {

            @Test
            @DisplayName("예외가 발생한다")
            void 예외가_발생한다() {
                // given
                final var email = "donggi@sendzy.com";
                final var password = "1234567";

                // when & then
                assertThatThrownBy(() -> new Member(email, password))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("비밀번호는 8자 이상이어야 합니다.");
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 비밀번호가_영문_대문자_영문_소문자_숫자_특수문자_중_3가지_이상을_포함하지_않으면 {

            @Test
            @DisplayName("예외가 발생한다")
            void 예외가_발생한다() {
                // given
                final var email = "donggi@sendzy.com";
                final var password = "password";

                // when & then
                assertThatThrownBy(() -> new Member(email, password))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("비밀번호는 영문 대문자, 영문 소문자, 숫자, 특수문자 중 3가지 이상을 포함해야 합니다.");
            }
        }
    }

}

class Member {

    private Long id;
    private String email;
    private String password;
    private LocalDateTime createdAt;

    public Member(final String email, final String password) {
        validate(email, password);
        this.id = 1L;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    private void validate(final String email, final String password) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("이메일은 필수입니다.");
        }

        if (email.length() > 100) {
            throw new IllegalArgumentException("이메일은 100자를 넘을 수 없습니다.");
        }

        if (email.length() < 5) {
            throw new IllegalArgumentException("이메일은 5자 이상이어야 합니다.");
        }

        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new IllegalArgumentException("이메일 형식이 아닙니다.");
        }

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 필수입니다.");
        }

        if (password.length() > 32) {
            throw new IllegalArgumentException("비밀번호는 32자를 넘을 수 없습니다.");
        }

        if (password.length() < 8) {
            throw new IllegalArgumentException("비밀번호는 8자 이상이어야 합니다.");
        }

        if (!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$")) {
            throw new IllegalArgumentException("비밀번호는 영문 대문자, 영문 소문자, 숫자, 특수문자 중 3가지 이상을 포함해야 합니다.");
        }
    }
}
