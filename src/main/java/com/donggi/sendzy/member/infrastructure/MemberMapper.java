package com.donggi.sendzy.member.infrastructure;

import com.donggi.sendzy.member.domain.Member;
import com.donggi.sendzy.member.domain.MemberRepository;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper extends MemberRepository {

    @Insert("INSERT INTO member (email, password, created_at) VALUES (#{email}, #{password}, #{createdAt})")
    int save(Member member);

    @Select("SELECT EXISTS (SELECT 1 FROM member WHERE email = #{email})")
    boolean existsByEmail(String email);

    @Delete("DELETE FROM member")
    void deleteAll();
}
