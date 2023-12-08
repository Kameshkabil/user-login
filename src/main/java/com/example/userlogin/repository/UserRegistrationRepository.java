package com.example.userlogin.repository;

import com.example.userlogin.entity.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRegistrationRepository extends JpaRepository<UserRegistration , Long> {

    Optional<UserRegistration> findByEmail(String emailId);
}
