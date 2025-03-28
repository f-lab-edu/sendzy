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
        final var senderEmail = TestUtils.DEFAULT_EMAIL;
        final var receiverEmail = "hello@sendzy.com";
        memberRepository.create(new Member(senderEmail, TestUtils.DEFAULT_ENCODED_PASSWORD));
        memberRepository.create(new Member(receiverEmail, TestUtils.DEFAULT_ENCODED_PASSWORD));
        final var sender = memberRepository.findByEmail(senderEmail).get();
        final var receiver = memberRepository.findByEmail(receiverEmail).get();

        // remittance_request의 sender_id, receiver_id 에는 member 테이블의 id 필드를
        // 외래키 제약 조건으로 가지고 있어 실제 존재하는 ID 값을 설정
        var expected = new RemittanceRequest(
            sender.getId(),
            receiver.getId(),
            RemittanceRequestStatus.PENDING,
            TestUtils.DEFAULT_AMOUNT
        );

        // when
        final var actual = remittanceRequestRepository.create(expected);

        // then
        assertThat(actual).isEqualTo(expected.getId());
    }
}
