package com.nikhilanand.taskmanagement.controller;

import com.nikhilanand.taskmanagement.exchange.request.AuthRequest;
import com.nikhilanand.taskmanagement.exchange.request.RegisterRequest;
import com.nikhilanand.taskmanagement.exchange.response.AuthResponse;
import com.nikhilanand.taskmanagement.global.GlobalVariables;
import com.nikhilanand.taskmanagement.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(GlobalVariables.APP_API_ENDPOINT)
public class AuthController {

    @Autowired
    AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }


    @GetMapping("/home")
    ResponseEntity<String> getHome() {
        return ResponseEntity.ok().body("home");
    }


}