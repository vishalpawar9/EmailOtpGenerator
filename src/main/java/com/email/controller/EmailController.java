package com.email.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.email.api.response.EmailApiResponse;
import com.email.model.EmailRequest;

@RestController
@RequestMapping("/api")
public interface EmailController {

    @PostMapping("/send-email")
    ResponseEntity<EmailApiResponse> sendEmail(EmailRequest emailRequest);

    @PostMapping("/check-otp")
	ResponseEntity<String> checkOTP(String email, String otp);

}