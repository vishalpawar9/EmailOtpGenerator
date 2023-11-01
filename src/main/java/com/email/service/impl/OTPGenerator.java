package com.email.service.impl;

import org.apache.commons.text.RandomStringGenerator;

public class OTPGenerator {
    public static OTP generateOTP(int length, long validityMinutes) {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
            .withinRange('0', '9')
            .build();
        String otp = generator.generate(length);
        long currentTimeMillis = System.currentTimeMillis();
        long expirationTimeMillis = currentTimeMillis + (validityMinutes * 60 * 1000); // Convert minutes to milliseconds

        return new OTP(otp, expirationTimeMillis);
    }

    public static class OTP {
        private final String code;
        private final long expirationTimeMillis;

        public OTP(String code, long expirationTimeMillis) {
            this.code = code;
            this.expirationTimeMillis = expirationTimeMillis;
        }

        public String getCode() {
            return code;
        }

        public long getExpirationTimeMillis() {
            return expirationTimeMillis;
        }
    }
}
