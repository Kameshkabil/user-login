package com.example.userlogin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DashboardController {

    @Autowired
    UserRegistrationController userRegistrationController;

    @GetMapping("/dashboard")
    public String getDashboard(@RequestHeader(value = "Authorization", defaultValue = "") String auth){
//
//        userRegistrationController.verify(auth);
        return "This is Dashboard Page";
    }


}
