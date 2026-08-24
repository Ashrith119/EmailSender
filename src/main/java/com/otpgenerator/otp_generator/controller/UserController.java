package com.otpgenerator.otp_generator.controller;

import com.otpgenerator.otp_generator.entity.User;
import com.otpgenerator.otp_generator.model.OtpVerificationRequest;
import com.otpgenerator.otp_generator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/register")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> register(
            @RequestBody User user
            ){
        userService.register(user);
        return ResponseEntity.ok("Registration successful.OTP sent to email");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(
            @RequestBody OtpVerificationRequest request) {

        userService.verifyOtp(request);

        return ResponseEntity.ok(
                "Email verified successfully"
        );
    }
}
