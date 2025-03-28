package com.donggi.sendzy.remittance.controller;

import com.donggi.sendzy.common.security.CustomUserDetails;
import com.donggi.sendzy.remittance.application.RemittanceRequestApplicationService;
import com.donggi.sendzy.remittance.controller.dto.RemittanceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/remittance")
public class RemittanceRestController {

    private final RemittanceRequestApplicationService remittanceRequestApplicationService;

    /**
     * 송금 요청
     * @param request 송금 요청 정보
     * @param userDetails 로그인 정보
     */
    @PostMapping
    public void remittance(@RequestBody final RemittanceRequest request, @AuthenticationPrincipal final CustomUserDetails userDetails) {
        final var senderId = userDetails.getMemberId();
        remittanceRequestApplicationService.createRemittanceRequest(senderId, request.receiverId(), request.amount());
    }
}
