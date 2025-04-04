package com.donggi.sendzy.remittance.controller;

import com.donggi.sendzy.common.security.CustomUserDetails;
import com.donggi.sendzy.remittance.application.RemittanceRequestProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/remittance")
public class RemittanceRequestRestController {

    private final RemittanceRequestProcessor remittanceRequestProcessor;

    /**
     * 송금 요청 수락
     * @param requestId 송금 요청 ID
     * @param userDetails 로그인 정보
     */
    @PostMapping("/{requestId}/accept")
    public void accept(@PathVariable final long requestId, @AuthenticationPrincipal final CustomUserDetails userDetails) {
        final var receiverId = userDetails.getMemberId();
        remittanceRequestProcessor.handleAcceptance(requestId, receiverId);
    }

    /**
     * 송금 요청 거절
     * @param requestId 송금 요청 ID
     * @param userDetails 로그인 정보
     */
    @PostMapping("/{requestId}/reject")
    public void reject(@PathVariable final long requestId, @AuthenticationPrincipal final CustomUserDetails userDetails) {
        final var receiverId = userDetails.getMemberId();
        remittanceRequestProcessor.handleRejection(requestId, receiverId);
    }
}
