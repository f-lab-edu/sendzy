package com.donggi.sendzy.account.infrastructure;

import com.donggi.sendzy.account.domain.Account;
import com.donggi.sendzy.account.domain.AccountRepository;
import com.donggi.sendzy.account.domain.TestAccountRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface AccountMapper extends AccountRepository, TestAccountRepository {
    Long create(final Account account);

    Optional<Account> findByMemberId(final Long memberId);

    void deleteAll();

    void updatePendingAmount(@Param("id") final Long id, @Param("pendingAmount") final Long pendingAmount);

    void updateBalance(@Param("id") final Long id, @Param("balance") final Long balance);
}
