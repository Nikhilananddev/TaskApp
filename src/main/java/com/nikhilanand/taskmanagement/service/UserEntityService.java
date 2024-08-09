package com.nikhilanand.taskmanagement.service;

import com.nikhilanand.taskmanagement.exchange.request.AddUserRequest;
import com.nikhilanand.taskmanagement.exchange.response.GetAllUserResponse;
import com.nikhilanand.taskmanagement.exchange.response.UserResponse;

public interface UserEntityService {


    UserResponse createUser(AddUserRequest addUserRequest);

    UserResponse updateUser(Long userId, AddUserRequest addUserRequest);

    UserResponse getUserById(Long userId);

    GetAllUserResponse getAllUsers();

    void deleteUser(Long userId);
}
