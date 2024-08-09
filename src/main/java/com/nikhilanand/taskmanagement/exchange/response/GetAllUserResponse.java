package com.nikhilanand.taskmanagement.exchange.response;

import com.nikhilanand.taskmanagement.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllUserResponse {

    @Builder.Default
    List<UserDTO> users = new ArrayList<>();
}
