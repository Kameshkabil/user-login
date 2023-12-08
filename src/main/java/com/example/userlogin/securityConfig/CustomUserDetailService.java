package com.example.userlogin.securityConfig;

import com.example.userlogin.entity.UserRegistration;
import com.example.userlogin.repository.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRegistrationRepository userRegistrationRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRegistration user = userRegistrationRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("username not found"));
        return new User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }
}
