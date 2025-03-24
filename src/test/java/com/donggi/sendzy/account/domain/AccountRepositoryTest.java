package com.donggi.sendzy.account.domain;

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
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TestAccountRepository testAccountRepository;

    @BeforeEach
    void setUp() {
        testAccountRepository.deleteAll();

        accountRepository.create(new Account(TestUtils.DEFAULT_MEMBER_ID));
    }

    @Test
    void 회원_ID로_계좌를_조회한다() {
        // given
        final var memberId = TestUtils.DEFAULT_MEMBER_ID;

        // when
        final var actual = accountRepository.findByMemberId(memberId).get();

        // then
        assertThat(actual.getMemberId()).isEqualTo(memberId);
    }

    @Test
    void 회원_ID로_계좌를_조회하고_조회된_계좌에_배타적_잠금을_건다() {
        // given
        final var memberId = TestUtils.DEFAULT_MEMBER_ID;

        // when
        final var actual = accountRepository.findByIdForUpdate(memberId).get();

        // then
        assertThat(actual.getMemberId()).isEqualTo(memberId);
    }
}
