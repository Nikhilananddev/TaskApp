package com.nikhilanand.taskmanagement.service;


import com.nikhilanand.taskmanagement.exception.user.UserEmailIdAlreadyExistsException;
import com.nikhilanand.taskmanagement.exception.user.UserNameAlreadyExistException;
import com.nikhilanand.taskmanagement.exchange.request.AuthRequest;
import com.nikhilanand.taskmanagement.exchange.request.RegisterRequest;
import com.nikhilanand.taskmanagement.exchange.response.AuthResponse;
import com.nikhilanand.taskmanagement.global.Role;
import com.nikhilanand.taskmanagement.model.User;
import com.nikhilanand.taskmanagement.model.UserEntity;
import com.nikhilanand.taskmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTService jwtService;


    @Autowired
    AuthenticationManager authenticationManager;

    public AuthResponse login(AuthRequest request) {


        System.out.println("AuthService " + request.getUsername());

        System.out.println("AuthService " + request.getPassword());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));
        Optional<UserEntity> userEntity = userRepository.findByUserName(request.getUsername());
        if (!userEntity.isPresent())
            throw new UsernameNotFoundException("user not found");

        String jwtToken = jwtService.generateToken(new User(userEntity.get()));
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public AuthResponse register(RegisterRequest request) {

        if (request.getRole() == null) {
            request.setRole(Role.USER);
        }

        boolean isUserEmailIdExist = userRepository.existsByEmail(request.getEmail());

        boolean existsByUserName = userRepository.existsByUserName(request.getUserName());


        if (isUserEmailIdExist) {
            try {
                throw new UserEmailIdAlreadyExistsException(request.getEmail() + " email id is already exist");
            } catch (UserEmailIdAlreadyExistsException e) {
                throw new RuntimeException(e);
            }
        }

        if (existsByUserName) {
            try {
                throw new UserNameAlreadyExistException(request.getUserName() + " userName id is already exist");
            } catch (UserNameAlreadyExistException e) {
                throw new RuntimeException(e);
            }
        }


        UserEntity user = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .userName(request.getUserName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        String jwtToken = jwtService.generateToken(new User(user));
        userRepository.save(user);

        return AuthResponse.builder()
                .accessToken(jwtToken)
                .build();

    }

    public User getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }
}
