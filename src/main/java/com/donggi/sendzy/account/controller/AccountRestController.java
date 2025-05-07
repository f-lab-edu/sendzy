package com.donggi.sendzy.account.controller;

import com.donggi.sendzy.account.application.AccountBalanceQueryService;
import com.donggi.sendzy.account.dto.AccountBalanceResponse;
import com.donggi.sendzy.common.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/account")
@RestController
public class AccountRestController {

    private final AccountBalanceQueryService accountBalanceQueryService;

    @GetMapping("/balance")
    public AccountBalanceResponse getBalance(@AuthenticationPrincipal final CustomUserDetails userDetails) {
        return accountBalanceQueryService.getBalanceWithRequestExpiredCheck(userDetails.getMemberId());
    }
}
