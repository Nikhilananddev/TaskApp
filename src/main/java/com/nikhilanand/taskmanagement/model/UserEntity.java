package com.nikhilanand.taskmanagement.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nikhilanand.taskmanagement.global.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userName;

    private String password;

    private String firstName;


    private String lastName;

    private String email;


    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Builder.Default
    @JsonManagedReference
    private Set<TaskEntity> tasks = new HashSet<>();


}
