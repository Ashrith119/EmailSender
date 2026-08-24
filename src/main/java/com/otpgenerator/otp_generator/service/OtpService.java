package com.otpgenerator.otp_generator.service;

import com.otpgenerator.otp_generator.entity.OtpVerification;
import com.otpgenerator.otp_generator.repository.OtpRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final OtpRepository otpRepository;
    public String generateOtp(){
        SecureRandom random = new SecureRandom();
        return String.valueOf(100000 + random.nextInt(900000));
    }
    public String createOtp(String email){

        // if the user clicks on resend otp for same email,then first delete that email and its data
        otpRepository.deleteByEmail(email);

        String otp = generateOtp();

        OtpVerification otpVerification = new OtpVerification();
        otpVerification.setEmail(email);
        otpVerification.setOtp(otp);

        otpVerification.setExpiryTime(LocalDateTime.now().plusMinutes(5));

        otpRepository.save(otpVerification);

        return otp;
    }
    public boolean verifyOtp(String email,String enteredOtp){
        Optional<OtpVerification> optionalOtp = otpRepository.findByEmail(email);

        if(optionalOtp.isEmpty()) return false;

        // converts optionalOtp result into OtpVerification object
        OtpVerification otpVerification = optionalOtp.get();

        if(otpVerification.getExpiryTime().isBefore(LocalDateTime.now())) return false;

        return otpVerification.getOtp().equals(enteredOtp);
    }
    @Transactional
    public void deleteOtp(String email){
        otpRepository.deleteByEmail(email);
    }
}
