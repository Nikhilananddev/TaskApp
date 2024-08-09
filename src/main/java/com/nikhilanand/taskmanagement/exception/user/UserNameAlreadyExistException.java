package com.nikhilanand.taskmanagement.exception.user;

public class UserNameAlreadyExistException extends Exception {
    public UserNameAlreadyExistException(String message) {
        super(message);
    }
}
