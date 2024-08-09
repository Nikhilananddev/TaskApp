package com.nikhilanand.taskmanagement.service;


import com.nikhilanand.taskmanagement.exchange.request.AddTaskRequest;
import com.nikhilanand.taskmanagement.exchange.response.AddTaskResponse;
import com.nikhilanand.taskmanagement.exchange.response.GetAllTaskResponse;
import com.nikhilanand.taskmanagement.exchange.response.TaskResponse;

public interface TaskService {

    AddTaskResponse createTask(AddTaskRequest addTaskRequest);

    GetAllTaskResponse getAllTasks();

    TaskResponse getTaskById(Long id);

    TaskResponse updateTask(Long id, AddTaskRequest updateTaskRequest);

    TaskResponse assignTask(Long taskId, Long userId);

    void deleteTaskById(Long id);

    GetAllTaskResponse getAllTasksByUserId(Long userId);

    GetAllTaskResponse getAllTasksBySearch(String search);


}
