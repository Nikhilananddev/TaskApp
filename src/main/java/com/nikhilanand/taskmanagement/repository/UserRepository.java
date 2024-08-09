package com.nikhilanand.taskmanagement.repository;

import com.nikhilanand.taskmanagement.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
//    Optional<UserEntity> findById(String username);

    boolean existsByEmail(String email);

    boolean existsByUserName(String username);

    Optional<UserEntity> findByUserName(String userName);

}
