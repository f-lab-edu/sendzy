package com.donggi.sendzy.member.domain;

public interface MemberRepository {

    /**
     * 회원을 저장합니다.
     * @param member 저장할 회원
     * @return 저장된 행의 수
     */
    int save(Member member);

    /**
     * 이메일로 회원이 존재하는지 확인합니다.
     * @param email 이메일
     * @return 회원이 존재하면 true, 존재하지 않으면 false
     */
    boolean existsByEmail(String email);

    /**
     * 저장된 모든 회원을 삭제합니다.
     */
    void deleteAll();
}
