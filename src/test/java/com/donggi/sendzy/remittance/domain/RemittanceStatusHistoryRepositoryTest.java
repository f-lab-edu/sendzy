package com.donggi.sendzy.remittance.domain;

import com.donggi.sendzy.remittance.domain.repository.RemittanceStatusHistoryRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@MybatisTest
public class RemittanceStatusHistoryRepositoryTest {

    @Autowired
    private RemittanceStatusHistoryRepository remittanceStatusHistoryRepository;

    @Test
    void 송금_요청_상태_변경_이력을_저장하고_ID를_반환한다() {
        // given
        final var requestId = 1L;
        final var senderId = 1L;
        final var receiverId = 1L;
        final var amount = 100L;

        final var expected = new RemittanceStatusHistory(
            requestId,
            senderId,
            receiverId,
            amount,
            RemittanceRequestStatus.PENDING
        );

        // when
        final var actual = remittanceStatusHistoryRepository.create(expected);

        // then
        assertThat(actual).isEqualTo(expected.getId());
    }
}
