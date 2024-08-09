package com.nikhilanand.taskmanagement.controller;

import com.nikhilanand.taskmanagement.exchange.request.AddTaskRequest;
import com.nikhilanand.taskmanagement.exchange.response.AddTaskResponse;
import com.nikhilanand.taskmanagement.exchange.response.GetAllTaskResponse;
import com.nikhilanand.taskmanagement.exchange.response.TaskResponse;
import com.nikhilanand.taskmanagement.global.GlobalVariables;
import com.nikhilanand.taskmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(GlobalVariables.TASK_APP_API_ENDPOINT)
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/add")
    public ResponseEntity<AddTaskResponse> createTask(@RequestBody AddTaskRequest addTaskRequest) {


        AddTaskResponse addTaskResponse = taskService.createTask(addTaskRequest);
        if (addTaskResponse != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(addTaskResponse);

        return ResponseEntity.badRequest().body(null);
    }


    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long taskId) {


        TaskResponse taskResponse = taskService.getTaskById(taskId);
        if (taskResponse != null)
            return ResponseEntity.status(HttpStatus.OK).body(taskResponse);

        return ResponseEntity.badRequest().body(null);
    }


    @GetMapping("/task/{search}")
    public ResponseEntity<GetAllTaskResponse> getTaskSearch(@PathVariable String search) {


        GetAllTaskResponse allTaskResponse = taskService.getAllTasksBySearch(search);
        if (allTaskResponse != null)
            return ResponseEntity.status(HttpStatus.OK).body(allTaskResponse);

        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/task/userId/{userId}")
    public ResponseEntity<GetAllTaskResponse> getTaskSearch(@PathVariable Long userId) {


        GetAllTaskResponse allTaskResponse = taskService.getAllTasksByUserId(userId);
        if (allTaskResponse != null)
            return ResponseEntity.status(HttpStatus.OK).body(allTaskResponse);

        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping()
    public ResponseEntity<GetAllTaskResponse> getAllTasks() {
        GetAllTaskResponse getAllTaskResponse = taskService.getAllTasks();
        if (getAllTaskResponse != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(getAllTaskResponse);

        return ResponseEntity.badRequest().body(null);
    }


    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long taskId, @RequestBody AddTaskRequest updateTaskRequest) {

        TaskResponse taskResponse = taskService.updateTask(taskId, updateTaskRequest);

        if (taskResponse != null)
            return ResponseEntity.status(201).body(taskResponse);

        return ResponseEntity.badRequest().body(null);
    }


    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTaskById(taskId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<TaskResponse> assignTaskToUser(
            @PathVariable Long taskId,
            @PathVariable Long userId) {
        TaskResponse updatedTask = taskService.assignTask(taskId, userId);
        if (updatedTask != null)
            return ResponseEntity.ok(updatedTask);

        return ResponseEntity.badRequest().body(null);
    }
}
