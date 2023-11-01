package com.email.service;

import org.springframework.stereotype.Service;

import com.email.api.response.EmailApiResponse;
import com.email.model.EmailRequest;

@Service
public interface EmailService {

    EmailApiResponse sendEmail(EmailRequest emailRequest);
}