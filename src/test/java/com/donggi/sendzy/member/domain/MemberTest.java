package com.donggi.sendzy.member.domain;

import com.donggi.sendzy.common.exception.ValidException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.donggi.sendzy.member.TestUtils.DEFAULT_EMAIL;
import static com.donggi.sendzy.member.TestUtils.DEFAULT_ENCODED_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class MemberTest {

    @Nested
    class 멤버_등록_시 {

        @Nested
        class 요청이_정상적이면 {

            @Test
            void 멤버가_생성된다() {
                // given
                final var email = DEFAULT_EMAIL;
                final var password = DEFAULT_ENCODED_PASSWORD;

                // when
                final var actual = new Member(email, password);

                // then
                assertThat(actual).isNotNull();
            }
        }

        @Nested
        class 이메일이 {

            @Test
            void 포함되지_않으면_예외가_발생한다() {
                // given
                final var password = DEFAULT_ENCODED_PASSWORD;

                // when & then
                assertThatThrownBy(() -> new Member(null, password))
                        .isInstanceOf(ValidException.class)
                        .hasMessage("email 은/는 null 또는 공백이 될 수 없습니다.");
            }

            @Test
            void 이메일이_300자를_넘어가면_예외가_발생한다() {
                // given
                final var email = "a".repeat(301);
                final var password = DEFAULT_ENCODED_PASSWORD;

                // when & then
                assertThatThrownBy(() -> new Member(email, password))
                        .isInstanceOf(ValidException.class)
                        .hasMessage("email 의 길이는 300 글자 이하여야 합니다.");
            }

            @Test
            void 이메일이_5자_미만이면_예외가_발생한다() {
                // given
                final var email = "a".repeat(4);
                final var password = DEFAULT_ENCODED_PASSWORD;

                // when & then
                assertThatThrownBy(() -> new Member(email, password))
                        .isInstanceOf(ValidException.class)
                        .hasMessage("email 의 길이는 5 글자 이상이어야 합니다.");
            }

            @Test
            void 이메일이_이메일_형식이_아니면_예외가_발생한다() {
                // given
                final var email = "donggi";
                final var password = DEFAULT_ENCODED_PASSWORD;

                // when & then
                assertThatThrownBy(() -> new Member(email, password))
                        .isInstanceOf(ValidException.class)
                        .hasMessage("email 은/는 유효한 이메일 형식 (예: user@example.com) 조건을 충족해야 합니다.");
            }
        }

        @Nested
        class 비밀번호가 {

            @Test
            void 포함되지_않으면_예외가_발생한다() {
                // given
                final var email = DEFAULT_EMAIL;

                // when & then
                assertThatThrownBy(() -> new Member(email, null))
                        .isInstanceOf(ValidException.class)
                        .hasMessage("encodedPassword 은/는 null 또는 공백이 될 수 없습니다.");
            }

            @Test
            void _300자를_넘어가면_예외가_발생한다() {
                // given
                final var email = DEFAULT_EMAIL;
                final var password = "a".repeat(301);

                // when & then
                assertThatThrownBy(() -> new Member(email, password))
                        .isInstanceOf(ValidException.class)
                        .hasMessage("encodedPassword 의 길이는 300 글자 이하여야 합니다.");
            }

            @Test
            void _60자_미만이면_예외가_발생한다() {
                // given
                final var email = DEFAULT_EMAIL;
                final var password = "a".repeat(59);

                // when & then
                assertThatThrownBy(() -> new Member(email, password))
                        .isInstanceOf(ValidException.class)
                        .hasMessage("encodedPassword 의 길이는 60 글자 이상이어야 합니다.");
            }
        }
    }

}
