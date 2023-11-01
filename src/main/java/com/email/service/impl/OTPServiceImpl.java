package com.email.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.email.service.OTPService;

@Service
public class OTPServiceImpl implements OTPService {

    private Map<String, OTPData> otpStorage = new HashMap<>();

    @Override
    public void storeOTP(String email, String otp, long expirationTimeMillis) {
        otpStorage.put(email, new OTPData(otp, expirationTimeMillis));
    }

    @Override
    public String retrieveOTP(String email) {
        OTPData otpData = otpStorage.get(email);
        if (otpData != null && otpData.isValid()) {
            return otpData.getOtp();
        }
        return null;
    }

    @Override
    public boolean isOTPValid(String email, String otp) {
        String storedOTP = retrieveOTP(email);
        return storedOTP != null && storedOTP.equals(otp);
    }

    private static class OTPData {
        private final String otp;
        private final long expirationTimeMillis;

        public OTPData(String otp, long expirationTimeMillis) {
            this.otp = otp;
            this.expirationTimeMillis = expirationTimeMillis;
        }

        public String getOtp() {
            return otp;
        }

        public boolean isValid() {
            return System.currentTimeMillis() <= expirationTimeMillis;
        }
    }
}
