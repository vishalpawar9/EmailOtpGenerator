package com.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
@EnableSwagger2
@SpringBootApplication
public class EmailapiApplication {

	public static void main(final String[] args) {
		SpringApplication.run(EmailapiApplication.class, args);
	}
}
