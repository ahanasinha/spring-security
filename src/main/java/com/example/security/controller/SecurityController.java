package com.example.security.controller;

import com.example.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @GetMapping("/login")
    public Map<String, String> login(@RequestParam String username, @RequestParam String password) {
        return securityService.loginService(username, password);
    }

    @GetMapping("/status")
    public Map<String, String> status(@RequestParam String token) {
        return securityService.getStatus(token);
    }
}
