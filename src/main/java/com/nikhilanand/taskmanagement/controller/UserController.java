package com.nikhilanand.taskmanagement.controller;


import com.nikhilanand.taskmanagement.exchange.request.AddUserRequest;
import com.nikhilanand.taskmanagement.exchange.response.GetAllUserResponse;
import com.nikhilanand.taskmanagement.exchange.response.UserResponse;
import com.nikhilanand.taskmanagement.global.GlobalVariables;
import com.nikhilanand.taskmanagement.service.UserEntityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(GlobalVariables.APP_API_ENDPOINT + "/users")
public class UserController {
    @Autowired
    private UserEntityService userService;


//        @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody AddUserRequest addUserRequest) {

        UserResponse createdUser = userService.createUser(addUserRequest);
        if (createdUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        UserResponse user = null;
        user = userService.getUserById(userId);
        if (user != null) {
            return ResponseEntity.ok().body(user);
        }


        return ResponseEntity.badRequest().body(null);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserResponse> updateUserById(@PathVariable Long userId, @Valid @RequestBody AddUserRequest addUserRequest) {

        UserResponse user = null;

        user = userService.updateUser(userId, addUserRequest);
        if (user != null) {
            return ResponseEntity.ok().body(user);
        }


        return ResponseEntity.badRequest().body(null);
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<GetAllUserResponse> getAllUsers() {
        GetAllUserResponse users = userService.getAllUsers();
        if (users != null) {
            return ResponseEntity.ok().body(users);
        }
        return ResponseEntity.badRequest().body(null);
    }

    //    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/remove/{userId}")
    public ResponseEntity<?> removeUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().body("User has been deleted");
    }

}

