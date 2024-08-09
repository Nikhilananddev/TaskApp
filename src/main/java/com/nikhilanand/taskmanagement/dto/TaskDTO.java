package com.nikhilanand.taskmanagement.dto;

import com.nikhilanand.taskmanagement.global.Priority;
import com.nikhilanand.taskmanagement.global.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTO {

    private Long id;

    @NotBlank(message = "Title is mandatory")
    @Size(max = 100, message = "Title can be at most 100 characters long")
    private String title;

    @Size(max = 500, message = "Description can be at most 500 characters long")
    private String description;

    @Enumerated(EnumType.STRING)
//    @Builder.Default
    private Status status;


    @Enumerated(EnumType.STRING)
//    @Builder.Default
    private Priority priority;

    @FutureOrPresent(message = "Due date must be today or in the future")
    private LocalDate dueDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long userId;


}
