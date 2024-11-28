package com.donggi.sendzy.member.integration;

import com.donggi.sendzy.member.TestUtils;
import com.donggi.sendzy.member.application.SignupService;
import com.donggi.sendzy.member.dto.LoginRequest;
import com.donggi.sendzy.member.dto.SignupRequest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private SignupService signupService;

    private static final String LOGIN_URL = "/v1/login";

    @Nested
    class 로그인_요청이 {

        @Nested
        class 정상적이면 {

            @Test
            void _200() {
                // given
                signupService.signup(new SignupRequest(TestUtils.DEFAULT_EMAIL, TestUtils.DEFAULT_RAW_PASSWORD));
                var expected = new LoginRequest(TestUtils.DEFAULT_EMAIL, TestUtils.DEFAULT_RAW_PASSWORD);

                // when & then
                given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .body(expected)
                .when()
                    .post(LOGIN_URL)
                .then()
                    .statusCode(HttpStatus.OK.value());
            }
        }
    }
}