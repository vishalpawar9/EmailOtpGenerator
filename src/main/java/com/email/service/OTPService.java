package com.email.service;

public interface OTPService {
    void storeOTP(String email, String otp, long expirationTimeMillis);
    String retrieveOTP(String email);
    boolean isOTPValid(String email, String otp);
}
