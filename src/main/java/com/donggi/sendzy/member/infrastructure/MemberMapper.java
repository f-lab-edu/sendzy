package com.donggi.sendzy.member.infrastructure;

import com.donggi.sendzy.member.domain.Member;
import com.donggi.sendzy.member.domain.MemberRepository;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper extends MemberRepository {

    @Insert("INSERT INTO member (email, password, created_at) VALUES (#{email}, #{password}, #{createdAt})")
    int save(Member member);
}
