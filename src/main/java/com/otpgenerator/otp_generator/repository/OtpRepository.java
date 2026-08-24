package com.otpgenerator.otp_generator.repository;

import com.otpgenerator.otp_generator.entity.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpVerification,Long> {

    Optional<OtpVerification> findByEmail(String email);

    void deleteByEmail(String email);
}
