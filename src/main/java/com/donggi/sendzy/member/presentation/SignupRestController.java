package com.donggi.sendzy.member.presentation;

import com.donggi.sendzy.member.application.SignupService;
import com.donggi.sendzy.member.dto.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RestController 는 @Controller 와 @ResponseBody 가 합쳐진 어노테이션입니다.
 * @Controller 는 Spring MVC 컨트롤러임을 나타내는 어노테이션입니다.
 * @ResponseBody 는 메서드의 반환값을 HTTP 응답 본문으로 사용하겠다는 어노테이션입니다.
 *
 * @RequestMapping 은 요청 URL과 컨트롤러의 매핑을 지정하는 어노테이션입니다.
 */
@RestController
@RequestMapping("/v1/signup")
@RequiredArgsConstructor
public class SignupRestController {

    private final SignupService signupService;

    /**
     * @PostMapping 은 POST 요청을 처리하는 메서드임을 나타내는 어노테이션입니다.
     *
     * @RequestBody 는 HTTP 요청 본문을 자바 객체로 변환하겠다는 어노테이션입니다.
     */
    @PostMapping
    public void signup(@RequestBody SignupRequest request) {
        signupService.signup(request);
    }
}
