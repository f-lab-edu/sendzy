package com.donggi.sendzy.member.integration;

import com.donggi.sendzy.member.domain.Member;
import com.donggi.sendzy.member.domain.MemberRepository;
import com.donggi.sendzy.member.domain.TestMemberRepository;
import com.donggi.sendzy.member.dto.SignupRequest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static com.donggi.sendzy.member.TestUtils.*;
import static io.restassured.RestAssured.given;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignupIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestMemberRepository testMemberRepository;

    @Autowired
    private MemberRepository memberRepository;

    private static final String SIGNUP_URL = "/v1/signup";

    @BeforeEach
    void setUp() {
        testMemberRepository.deleteAll();
    }

    @Nested
    class 회원_가입_요청이 {

        @Nested
        class 정상적이면 {

            @Test
            void 회원을_등록한다() {
                // given
                final var expected = new SignupRequest(DEFAULT_EMAIL, DEFAULT_RAW_PASSWORD);

                // when & then
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

        @Nested
        class 이메일이_중복되면 {

            @Test
            void _409_Conflict를_반환한다() {
                // given
                memberRepository.create(new Member(DEFAULT_EMAIL, DEFAULT_ENCODED_PASSWORD));
                final var expected = new SignupRequest(DEFAULT_EMAIL, DEFAULT_RAW_PASSWORD);

                // when & then
                given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .body(expected)
                .when()
                    .post(SIGNUP_URL)
                .then()
                    .statusCode(HttpStatus.CONFLICT.value());
            }
        }
    }
}
