package com.donggi.sendzy.member.domain;

import java.util.Optional;

public interface MemberRepository {

    /**
     * 회원을 저장합니다.
     * @param member 저장할 회원
     * @return 저장된 회원의 ID
     */
    Long create(Member member);

    /**
     * 이메일로 회원이 존재하는지 확인합니다.
     * @param email 이메일
     * @return 회원이 존재하면 true, 존재하지 않으면 false
     */
    boolean existsByEmail(String email);

    /**
     * 이메일로 회원을 조회합니다.
     * @param email 이메일
     * @return 조회된 회원
     */
    Optional<Member> findByEmail(String email);
}
