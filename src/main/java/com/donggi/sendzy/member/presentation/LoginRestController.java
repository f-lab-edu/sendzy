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

    /**
     * 세션 생성을 위해 매개 변수로 HttpSession을 선언합니다.
     * Spring은 HttpSession을 생성해서 주입해줍니다.
     *
     * HttpServletRequest을 매개변수로 받아와서, getSession() 메서드를 호출하여 명시적으로 세션을 생성하는 방법도 있습니다.
     */
    @PostMapping
    public void login(
        @RequestBody final LoginRequest request,
        final HttpSession httpSession
    ) {
        authService.authenticate(request);
    }
}
