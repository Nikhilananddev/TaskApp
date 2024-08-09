package com.nikhilanand.taskmanagement.repository;


import com.nikhilanand.taskmanagement.model.TaskEntity;
import com.nikhilanand.taskmanagement.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {


//    List<TaskEntity> findByUserId(Long userId);

    @Query("SELECT t FROM TaskEntity t WHERE (:status IS NULL OR t.status = :status) " +
            "AND (:priority IS NULL OR t.priority = :priority) " +
            "AND (:dueDate IS NULL OR t.dueDate = :dueDate)")
    List<TaskEntity> findByFilters(
            @Param("status") String status,
            @Param("priority") String priority,
            @Param("dueDate") LocalDate dueDate);


    List<TaskEntity> findByTitleContainingIgnoreCase(String title);

    List<TaskEntity> findByDescriptionContainingIgnoreCase(String description);

    List<TaskEntity> findByUser(UserEntity user);

    @Query("SELECT t FROM TaskEntity t WHERE t.user.userId = :userId")
    List<TaskEntity> findAllTasksByUserId(@Param("userId") Long userId);

}
