package com.donggi.sendzy.remittance.domain;

import com.donggi.sendzy.member.TestUtils;
import com.donggi.sendzy.member.domain.Member;
import com.donggi.sendzy.member.domain.MemberRepository;
import com.donggi.sendzy.remittance.domain.repository.RemittanceRequestRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@MybatisTest
public class RemittanceRequestRepositoryTest {

    @Autowired
    private RemittanceRequestRepository remittanceRequestRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 송금_내역을_저장하고_ID를_반환한다() {
        // given
        final var senderId = memberRepository.create(new Member(TestUtils.DEFAULT_EMAIL, TestUtils.DEFAULT_ENCODED_PASSWORD));
        final var receiverId = memberRepository.create(new Member("hello@sendzy.com", TestUtils.DEFAULT_ENCODED_PASSWORD));

        // remittance_request의 sender_id, receiver_id 에는 member 테이블의 id 필드를
        // 외래키 제약 조건으로 가지고 있어 실제 존재하는 ID 값을 설정
        var expected = new RemittanceRequest(
            senderId,
            receiverId,
            RemittanceRequestStatus.PENDING,
            TestUtils.DEFAULT_AMOUNT
        );

        // when
        final var actual = remittanceRequestRepository.create(expected);

        // then
        assertThat(actual).isEqualTo(expected.getId());
    }
}
