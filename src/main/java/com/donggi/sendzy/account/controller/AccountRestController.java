package com.donggi.sendzy.account.controller;

import com.donggi.sendzy.account.domain.AccountService;
import com.donggi.sendzy.account.dto.AccountBalanceResponse;
import com.donggi.sendzy.common.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/account")
@RestController
public class AccountRestController {

    private final AccountService accountService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/balance")
    public AccountBalanceResponse getBalance(@AuthenticationPrincipal CustomUserDetails userDetails) {
        final var balance = accountService.findByMemberId(userDetails.getMemberId()).getBalance();
        return new AccountBalanceResponse(balance);
    }
}
