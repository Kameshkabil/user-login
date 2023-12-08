package com.example.userlogin.controller;

import com.example.userlogin.entity.JwtResponse;
import com.example.userlogin.entity.UserLoginRequest;
import com.example.userlogin.entity.UserRegistration;
import com.example.userlogin.repository.UserRegistrationRepository;
//import com.example.userlogin.securityConfig.GeneratJwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/user-registration")
public class UserRegistrationController {

    @Autowired
    UserRegistrationRepository userRegistrationRepository;

    @Autowired
    AuthenticationProvider authenticationProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping
    public UserRegistration createUserRegistration(@RequestBody UserRegistration userRegistration){
        UserRegistration userRegistration1 = new UserRegistration();
        userRegistration1.setUserName(userRegistration.getUserName());
        userRegistration1.setEmail(userRegistration.getEmail());
        userRegistration1.setPassword(passwordEncoder.encode(userRegistration.getPassword()));
        return this.userRegistrationRepository.save(userRegistration1);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> checkLoginUser(@RequestBody UserLoginRequest userLoginRequest){

        try{
            authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginRequest.getEmailId(), userLoginRequest.getPassword())
            );
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        UserRegistration userRegistration = userRegistrationRepository.findByEmail(userLoginRequest.getEmailId()).get();
        String token = generateJwtToken(userRegistration.getId(), userRegistration.getEmail());
        return ResponseEntity.ok(new JwtResponse(token));
//        Optional<UserRegistration> userOptional = userRegistrationRepository.findByEmail(userLoginRequest.getEmailId());
//
//        if (userOptional.isPresent() && userOptional.get().getPassword().equals(userLoginRequest.getPassword())){
//            Long userId = userOptional.get().getId();
//            String token = generateJwtToken(userId,userOptional.get().getEmail());
//            JwtResponse jwtResponse = new JwtResponse(token);
//            return ResponseEntity.ok(jwtResponse);
//        }else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        }
    }



    private String generateJwtToken(Long userId , String userEmail) {
        return Jwts.builder()
                .claim("userID" , userId)
                .claim("email" , userEmail)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date( System.currentTimeMillis()+3600000+1000))
                .signWith(SignatureAlgorithm.HS512, "kamesh-secret-key")
                .compact();
    }

    public void verify(String authorization)  {
//       try {
            Jwts.parser().setSigningKey("kamesh-secret-key").parseClaimsJws(authorization);
//        }catch (Exception e){
//            throw new Exception();
//        }


    }
}
