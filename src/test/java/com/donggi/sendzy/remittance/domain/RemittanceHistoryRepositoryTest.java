package com.donggi.sendzy.remittance.domain;

import com.donggi.sendzy.member.TestUtils;
import com.donggi.sendzy.remittance.domain.repository.RemittanceHistoryRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@MybatisTest
public class RemittanceHistoryRepositoryTest {

    @Autowired
    private RemittanceHistoryRepository remittanceHistoryRepository;

    @Test
    void 송금_내역을_저장하고_ID를_반환한다() {
        // given
        var expected = new RemittanceHistory(
            TestUtils.DEFAULT_REMITTANCE_REQUEST_ID,
            TestUtils.DEFAULT_MEMBER_ID,
            TestUtils.DEFAULT_EMAIL,
            TestUtils.DEFAULT_DESCRIPTION,
            TestUtils.DEFAULT_AMOUNT,
            TestUtils.DEFAULT_BALANCE
        );

        // when
        final var actual = remittanceHistoryRepository.create(expected);

        // then
        assertThat(actual).isEqualTo(expected.getId());
    }
}
