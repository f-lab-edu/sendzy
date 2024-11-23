package com.donggi.sendzy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @SpringBootApplication 은 Spring Boot 애플리케이션임을 나타내는 어노테이션입니다.
 * 내부적으로 @Configuration, @EnableAutoConfiguration, @ComponentScan 을 포함하고 있습니다.
 */
@SpringBootApplication
public class SendzyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SendzyApplication.class, args);
	}

}
