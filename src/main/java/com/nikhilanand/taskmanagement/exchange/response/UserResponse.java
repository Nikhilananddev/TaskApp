package com.nikhilanand.taskmanagement.exchange.response;


import com.nikhilanand.taskmanagement.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private UserDTO userDetails;

}
