package com.otpgenerator.otp_generator.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpVerificationRequest {

    private String email;

    private String otp;
}
