package com.donggi.sendzy.member.domain;

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

    @Test
    void 멤버를_저장하고_저장된_행의_수를_반환한다() {
        // given
        final var email = "donggi@sendzy.com";
        final var password = "supperPass1word@#";

        // when
        final var actual = memberRepository.save(new Member(email, password));

        // then
        assertThat(actual).isEqualTo(1L);
    }
}
