package com.otpgenerator.otp_generator.service;

import com.otpgenerator.otp_generator.entity.User;
import com.otpgenerator.otp_generator.model.OtpVerificationRequest;
import com.otpgenerator.otp_generator.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final OtpService otpService;

    public void register(User user){

        user.setVerified(false);
        userRepository.save(user);

        String otp = otpService.createOtp(user.getEmail());

        System.out.println("Generated OTP : " + otp);

        emailService.sendOtp(user.getEmail(),otp);
    }
    @Transactional
    public void verifyOtp(OtpVerificationRequest request){

        boolean valid = otpService.verifyOtp(request.getEmail(),request.getOtp());

        if(!valid){
            throw new RuntimeException("Invalid or Expired OTP");
        }
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                ()-> new RuntimeException("User not found"));
        user.setVerified(true);
        userRepository.save(user);

        otpService.deleteOtp(request.getEmail());
    }
}
