package com.donggi.sendzy.member.domain;

public interface MemberRepository {

    /**
     * 회원을 저장합니다.
     * @param member 저장할 회원
     * @return 저장된 행의 수
     */
    int save(Member member);
}
