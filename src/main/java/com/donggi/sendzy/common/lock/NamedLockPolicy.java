package com.donggi.sendzy.common.lock;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 네임드 락의 식별자(key)와 타임아웃 설정을 정의합니다.
 *
 * - 락 이름은 "도메인:의도:세부내용" 형식으로 구성하여 명확한 의미 전달과 충돌 방지를 유도합니다.
 * - 타임아웃 값은 락 점유 대기 시간(seconds)으로, 상황에 따라 설정할 수 있습니다.
 *
 * 공통 락 정의를 enum으로 관리함으로써, 일관성 있는 락 네이밍과 정책 적용을 지원합니다.
 */
@Getter
@RequiredArgsConstructor
public enum NamedLockPolicy {

    REMITTANCE_EXPIRE_BATCH("remittance:expiration:batch", 0),
    ;

    private final String key;
    private final int timeout;
}
