package com.nikhilanand.taskmanagement.service.impl;

import com.nikhilanand.taskmanagement.dto.UserDTO;
import com.nikhilanand.taskmanagement.exception.user.UserEmailIdAlreadyExistsException;
import com.nikhilanand.taskmanagement.exception.user.UserNameAlreadyExistException;
import com.nikhilanand.taskmanagement.exception.user.UserNotFoundException;
import com.nikhilanand.taskmanagement.exchange.request.AddUserRequest;
import com.nikhilanand.taskmanagement.exchange.response.GetAllUserResponse;
import com.nikhilanand.taskmanagement.exchange.response.UserResponse;
import com.nikhilanand.taskmanagement.model.UserEntity;
import com.nikhilanand.taskmanagement.repository.UserRepository;
import com.nikhilanand.taskmanagement.service.UserEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserEntityServiceImpl implements UserEntityService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserResponse createUser(AddUserRequest addUserRequest) {

        String encodedPassword = passwordEncoder.encode(addUserRequest.getPassword());

        boolean isUserEmailIdExist = userRepository.existsByEmail(addUserRequest.getEmail());

        boolean username = userRepository.existsByUserName(addUserRequest.getUserName());

        if (isUserEmailIdExist) {
            try {
                throw new UserEmailIdAlreadyExistsException(addUserRequest.getEmail() + " email id is already exist");
            } catch (UserEmailIdAlreadyExistsException e) {
                throw new RuntimeException(e);
            }
        }

        if (username) {
            try {
                throw new UserNameAlreadyExistException(addUserRequest.getUserName() + " username id is already exist");
            } catch (UserNameAlreadyExistException e) {
                throw new RuntimeException(e);
            }
        }

        UserEntity userEntity = UserEntity.builder()
                .firstName(addUserRequest.getFirstName())
                .lastName(addUserRequest.getLastName())
                .userName(addUserRequest.getUserName())
                .email(addUserRequest.getEmail())
                .password(encodedPassword)
                .role(addUserRequest.getRole())
                .build();

        UserEntity savedUser = userRepository.save(userEntity);

        UserResponse userResponse = UserResponse.builder()
                .userDetails(modelMapper.map(savedUser, UserDTO.class))
                .build();


        return userResponse;

    }

    @Override
    public UserResponse updateUser(Long userId, AddUserRequest addUserRequest) {
        System.out.println(addUserRequest.toString());
        String encodedPassword = passwordEncoder.encode(addUserRequest.getPassword());

        Optional<UserEntity> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            try {
                throw new UserNotFoundException("User not found");
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
        }


        boolean isUserEmailIdExist = userRepository.existsByEmail(addUserRequest.getEmail());

        if (isUserEmailIdExist) {
            try {
                throw new UserEmailIdAlreadyExistsException(addUserRequest.getEmail() + " email id is already exist");
            } catch (UserEmailIdAlreadyExistsException e) {
                throw new RuntimeException(e);
            }
        }


        UserEntity userEntity = user.get();
        userEntity.setFirstName(addUserRequest.getFirstName());
        userEntity.setLastName(addUserRequest.getLastName());
        userEntity.setEmail(addUserRequest.getEmail());
        userEntity.setPassword(encodedPassword);
        userEntity.setRole(addUserRequest.getRole());

        UserEntity savedUser = userRepository.save(userEntity);


        UserResponse userResponse = UserResponse.builder()
                .userDetails(modelMapper.map(savedUser, UserDTO.class))
                .build();


        return userResponse;

    }

    @Override
    public UserResponse getUserById(Long userId) {

        Optional<UserEntity> user = userRepository.findById(userId);

        System.out.println("UserTest " + user);
        if (!user.isPresent()) {
            try {
                throw new UserNotFoundException("User not found");
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }

        }

        System.out.println("UserTest2 " + user);

        System.out.println("UserTest3 " + UserResponse.builder()
                .userDetails(modelMapper.map(user, UserDTO.class))
                .build());

        return UserResponse.builder()
                .userDetails(modelMapper.map(user, UserDTO.class))
                .build();
    }


    @Override
    public GetAllUserResponse getAllUsers() {


        List<UserEntity> userEntities = userRepository.findAll();

        List<UserDTO> userDTOList = new ArrayList<>();
        for (UserEntity user : userEntities) {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            userDTOList.add(userDTO);
        }
        return GetAllUserResponse.builder()
                .users(userDTOList)
                .build();
    }

    @Override
    public void deleteUser(Long userId) {

        Optional<UserEntity> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            try {
                throw new UserNotFoundException("User not found");
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }

        }

        userRepository.deleteById(userId);


    }
}
