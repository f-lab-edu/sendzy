package com.donggi.sendzy.member.presentation;

import com.donggi.sendzy.member.application.AuthService;
import com.donggi.sendzy.member.dto.LoginRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/login")
@RequiredArgsConstructor
public class LoginRestController {

    private final AuthService authService;

    @PostMapping
    public void login(@RequestBody final LoginRequest request, final HttpSession session) {
        authService.authenticate(request, session);
    }
}
