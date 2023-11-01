package com.email.service.impl;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.email.api.response.EmailApiResponse;
import com.email.model.EmailRequest;
import com.email.service.EmailService;
import com.email.service.OTPService;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	public OTPService otpService;

	@Override
	public EmailApiResponse sendEmail(EmailRequest emailRequest) {
		EmailApiResponse response = new EmailApiResponse();
		String host = "smtp.gmail.com";

		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("technology9049@gmail.com", "dtok ynqk ijoj bvab");
			}
		});

		session.setDebug(true);

		try {
			MimeMessage m = new MimeMessage(session);

			if (emailRequest.getFrom() != null) {
				m.setFrom(new InternetAddress(emailRequest.getFrom()));
			} else {
				response.setMessage("Email not sent Successfully");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
				return response;
			}

			if (emailRequest.getTo() != null) {
				System.out.println("To address: " + emailRequest.getTo());

				OTPGenerator.OTP otp = OTPGenerator.generateOTP(6, 10);
				String otpCode = otp.getCode();
				m.addRecipient(Message.RecipientType.TO, new InternetAddress(emailRequest.getTo()));

				// Include the OTP in the email message
				emailRequest.setMessage("This otp is valid only 10 minutes .Your OTP is: " + otpCode);

				// Store the OTP and its expiration time
				otpService.storeOTP(emailRequest.getTo(), otpCode, otp.getExpirationTimeMillis());
			} else {
				response.setMessage("Email not sent Successfully (No 'To' address provided)");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
				return response;
			}
			
			if (emailRequest.getSubject() != null) {
				m.setSubject(emailRequest.getSubject());
			}

			if (emailRequest.getMessage() != null) {
				m.setText(emailRequest.getMessage());
			}

			Transport.send(m);

			response.setMessage("Email sent Successfully");
			response.setStatus(HttpStatus.OK);

		} catch (MessagingException e) {
			e.printStackTrace();
			response.setMessage("Email not sent Successfully");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}
}
