package com.donggi.sendzy.remittance.infrastructure.expiration;

public class ExpirationQueueInterruptedException extends RuntimeException {

    public ExpirationQueueInterruptedException() {
        super("만료 큐에서 요청을 가져오는 도중 인터럽트되었습니다.");
    }
}
