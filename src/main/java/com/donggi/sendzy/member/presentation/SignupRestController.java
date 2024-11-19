package com.donggi.sendzy.member.presentation;

import com.donggi.sendzy.member.application.SignupService;
import com.donggi.sendzy.member.dto.SignupRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/signup")
public class SignupRestController {

    private final SignupService signupService;

    public SignupRestController(SignupService signupService) {
        this.signupService = signupService;
    }

    @PostMapping
    public void signup(@RequestBody SignupRequest request) {
        signupService.signup(request);
    }
}