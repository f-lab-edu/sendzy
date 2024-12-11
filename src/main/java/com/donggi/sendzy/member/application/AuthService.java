package com.donggi.sendzy.member.application;

import com.donggi.sendzy.member.dto.LoginRequest;
import com.donggi.sendzy.member.exception.InvalidPasswordException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    public void authenticate(final LoginRequest loginRequest, final HttpSession session) {
        try {
            final var authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.rawPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }
    }
}
