package com.nikhilanand.taskmanagement.service;


import com.nikhilanand.taskmanagement.model.User;
import com.nikhilanand.taskmanagement.model.UserEntity;
import com.nikhilanand.taskmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> optionalUserEntity = userRepository.findByUserName(username);

        if (!optionalUserEntity.isPresent()) throw new UsernameNotFoundException("User not found ");


        return new User(optionalUserEntity.get());
    }
}
