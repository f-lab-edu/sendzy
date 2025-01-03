package com.donggi.sendzy.account.domain;

import java.util.Optional;

public interface AccountRepository {

    /**
     * 계좌를 생성합니다.
     * @param account 생성할 계좌
     * @return 생성된 계좌의 ID
     */
    Long create(Account account);

    /**
     * 회원 ID로 계좌를 조회합니다.
     * @param memberId 회원 ID
     * @return 조회된 계좌
     */
    Optional<Account> findByMemberId(Long memberId);
}
