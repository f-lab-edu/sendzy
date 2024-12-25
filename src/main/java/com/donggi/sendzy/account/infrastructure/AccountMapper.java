package com.donggi.sendzy.account.infrastructure;

import com.donggi.sendzy.account.domain.Account;
import com.donggi.sendzy.account.domain.AccountRepository;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper extends AccountRepository {
    Long create(Account account);

    Account findByMemberId(Long memberId);

    void deleteAll();
}
