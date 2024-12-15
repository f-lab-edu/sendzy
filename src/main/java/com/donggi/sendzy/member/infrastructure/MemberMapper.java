package com.donggi.sendzy.member.infrastructure;

import com.donggi.sendzy.member.domain.Member;
import com.donggi.sendzy.member.domain.MemberRepository;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberMapper extends MemberRepository {

    int create(Member member);

    boolean existsByEmail(String email);

    void deleteAll();

    Optional<Member> findByEmail(String email);
}
