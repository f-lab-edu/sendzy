package com.donggi.sendzy.member.infrastructure;

import com.donggi.sendzy.member.domain.Member;
import com.donggi.sendzy.member.domain.MemberRepository;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper extends MemberRepository {

    int save(Member member);

    boolean existsByEmail(String email);

    void deleteAll();
}
