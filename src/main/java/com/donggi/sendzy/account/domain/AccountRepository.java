package com.donggi.sendzy.account.domain;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AccountRepository {

    /**
     * 계좌를 생성합니다.
     * @param account 생성할 계좌
     * @return 생성된 계좌의 ID
     */
    Long create(final Account account);

    /**
     * 회원 ID로 계좌를 조회합니다.
     * @param memberId 회원 ID
     * @return 조회된 계좌
     */
    Optional<Account> findByMemberId(final long memberId);

    /**
     * 회원 ID로 계좌를 조회하고, 조회된 계좌에 배타적 잠금(Exclusive Lock)을 겁니다.
     * @param memberId 회원 ID
     * @return 잠금이 설정된 계좌(Optional)
     */
    Optional<Account> findByMemberIdForUpdate(final long memberId);

    /**
     * 계좌를 업데이트합니다.
     * @param account 업데이트할 계좌
     */
    void update(final Account account);

    /**
     * 계좌 목록을 일괄 업데이트합니다.
     *
     * @param accounts 업데이트할 계좌 목록
     */
    void bulkUpdate(final List<Account> accounts);

    /**
     * 회원 ID 목록에 해당하는 계좌 목록을 조회합니다.
     *
     * @param senderIds 회원 ID 목록
     * @return 조회된 계좌 목록
     */
    List<Account> findAllByMemberIdIn(final Set<Long> senderIds);
}
