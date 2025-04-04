package com.donggi.sendzy.account.infrastructure;

import com.donggi.sendzy.account.domain.Account;
import com.donggi.sendzy.account.domain.AccountRepository;
import com.donggi.sendzy.account.domain.TestAccountRepository;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface AccountMapper extends AccountRepository, TestAccountRepository {
    Long create(final Account account);

    void deleteAll();

    void update(final Account account);

    Optional<Account> findByMemberId(final long memberId);

    Optional<Account> findByMemberIdForUpdate(final long memberId);
}
