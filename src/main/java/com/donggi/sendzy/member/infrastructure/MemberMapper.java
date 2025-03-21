package com.donggi.sendzy.member.infrastructure;

import com.donggi.sendzy.member.domain.Member;
import com.donggi.sendzy.member.domain.MemberRepository;
import com.donggi.sendzy.member.domain.TestMemberRepository;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberMapper extends MemberRepository, TestMemberRepository {

    Long create(Member member);

    boolean existsByEmail(String email);

    void deleteAll();

    Optional<Member> findByEmail(String email);
}
