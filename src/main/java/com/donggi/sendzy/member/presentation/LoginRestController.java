package com.donggi.sendzy.member.presentation;

import com.donggi.sendzy.common.security.AuthService;
import com.donggi.sendzy.member.dto.LoginRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/login")
public class LoginRestController {

    private final AuthService authService;
    /**
     * 세션 생성을 위해 매개 변수로 HttpSession을 선언합니다.
     * Spring은 HttpSession을 생성해서 주입해줍니다.
     *
     * HttpServletRequest을 매개변수로 받아와서, getSession() 메서드를 호출하여 명시적으로 세션을 생성하는 방법도 있습니다.
     */
    @PostMapping
    public ResponseEntity<Void> login(@RequestBody final LoginRequest request, final HttpSession httpSession) {
        authService.authenticate(request.email(), request.rawPassword());

        httpSession.setAttribute(
            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
            SecurityContextHolder.getContext()
        );
        return ResponseEntity.ok().build();
    }
}
