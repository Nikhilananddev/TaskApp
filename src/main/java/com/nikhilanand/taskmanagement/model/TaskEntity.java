package com.nikhilanand.taskmanagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nikhilanand.taskmanagement.global.Priority;
import com.nikhilanand.taskmanagement.global.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
@Builder
public class TaskEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.TODO;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Priority priority = Priority.LOW;

    private LocalDate dueDate;

    @ManyToOne
//    @JoinColumn(name = "userId", nullable = false)
    @JsonBackReference
    private UserEntity user;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
