package com.donggi.sendzy.member.domain;

import com.donggi.sendzy.member.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@MybatisTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
    }

    @Test
    void 멤버를_저장하고_저장된_행의_수를_반환한다() {
        // given
        final var email = TestUtils.DEFAULT_EMAIL;
        final var password = TestUtils.DEFAULT_ENCODED_PASSWORD;

        // when
        final var actual = memberRepository.create(new Member(email, password));

        // then
        assertThat(actual).isEqualTo(1L);
    }

    @Test
    void 이메일로_회원을_조회한다() {
        // given
        final var email = TestUtils.DEFAULT_EMAIL;
        final var password = TestUtils.DEFAULT_ENCODED_PASSWORD;
        final var expected = memberRepository.create(new Member(email, password));

        // when
        final var actual = memberRepository.findByEmail(email).get();

        // then
        assertThat(actual.getId()).isEqualTo(expected);
    }

    @Test
    void 이메일로_회원_조회시_등록된_이메일이_없으면_빈값을_반환한다() {
        // given
        final var email = TestUtils.DEFAULT_EMAIL;

        // when & then
        assertThat(memberRepository.findByEmail(email)).isEmpty();

    }
}
