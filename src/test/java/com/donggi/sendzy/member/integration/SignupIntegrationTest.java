package com.donggi.sendzy.member.integration;

import com.donggi.sendzy.member.dto.SignupRequest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignupIntegrationTest {

    @LocalServerPort
    private int port;

    private static final String SIGNUP_URL = "/v1/signup";

    @Nested
    class 회원_가입_요청이 {

        @Nested
        class 정상적이면 {

            @Test
            void 회원을_등록한다() {
                // given
                final var email = "donggi@sendzy.com";
                final var password = "PassWord123!@#";
                final var expected = new SignupRequest(email, password);

                // when
                given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .body(expected)
                .when()
                    .post(SIGNUP_URL)
                .then()
                    .statusCode(HttpStatus.OK.value());
            }

        }
    }

    // 회원 가입 요청이 정상적이면 회원을 등록한다
    // 회원 가입 요청이 이메일이 중복되면 409 Conflict를 반환한다
    // 회원 가입 요청이 이메일이 포함되지 않으면 400 Bad Request를 반환한다
    // 회원 가입 요청이 비밀번호가 포함되지 않으면 400 Bad Request를 반환한다
    // 회원 가입 요청이 비밀번호가 영문 대문자 영문 소문자 숫자 특수문자 중 3가지 이상을 포함하지 않으면 400 Bad Request를 반환한다
}
