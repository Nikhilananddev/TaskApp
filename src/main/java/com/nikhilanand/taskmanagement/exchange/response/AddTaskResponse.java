package com.nikhilanand.taskmanagement.exchange.response;

import com.nikhilanand.taskmanagement.dto.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTaskResponse {
    private TaskDTO task;
}
