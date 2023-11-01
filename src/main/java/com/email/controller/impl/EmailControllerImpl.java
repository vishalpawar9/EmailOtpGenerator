package com.email.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.email.api.response.EmailApiResponse;
import com.email.controller.EmailController;
import com.email.model.EmailRequest;
import com.email.service.EmailService;
import com.email.service.OTPService;
@RestController
public class EmailControllerImpl implements EmailController {

    @Autowired
    public EmailService emailService;

    @Autowired
    public OTPService otpService;
    @Override
    public ResponseEntity<EmailApiResponse> sendEmail(@RequestBody EmailRequest emailRequest) {
        EmailApiResponse response = emailService.sendEmail(emailRequest);
        return new ResponseEntity<>(response, response.getStatus());
    }
    @Override
    public ResponseEntity<String> checkOTP(@RequestParam String email, @RequestParam String otp) {
        if (otpService.isOTPValid(email, otp)) {
            return new ResponseEntity<>("OTP is valid", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("OTP is not valid", HttpStatus.BAD_REQUEST);
        }
    }
}