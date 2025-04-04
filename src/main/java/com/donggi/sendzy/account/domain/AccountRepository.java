package com.donggi.sendzy.account.domain;

import java.util.Optional;

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
}
