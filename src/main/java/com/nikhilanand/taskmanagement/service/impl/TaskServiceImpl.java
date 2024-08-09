package com.nikhilanand.taskmanagement.service.impl;

import com.nikhilanand.taskmanagement.dto.TaskDTO;
import com.nikhilanand.taskmanagement.exception.TaskNotFoundException;
import com.nikhilanand.taskmanagement.exception.user.UserNotFoundException;
import com.nikhilanand.taskmanagement.exchange.request.AddTaskRequest;
import com.nikhilanand.taskmanagement.exchange.response.AddTaskResponse;
import com.nikhilanand.taskmanagement.exchange.response.GetAllTaskResponse;
import com.nikhilanand.taskmanagement.exchange.response.TaskResponse;
import com.nikhilanand.taskmanagement.model.TaskEntity;
import com.nikhilanand.taskmanagement.model.UserEntity;
import com.nikhilanand.taskmanagement.repository.TaskRepository;
import com.nikhilanand.taskmanagement.repository.UserRepository;
import com.nikhilanand.taskmanagement.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AddTaskResponse createTask(AddTaskRequest addTaskRequest) {

        TaskEntity taskEntity = TaskEntity.builder()
                .title(addTaskRequest.getTitle())
                .description(addTaskRequest.getDescription())
                .status(addTaskRequest.getStatus())
                .priority(addTaskRequest.getPriority())
                .dueDate(addTaskRequest.getDueDate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();


        TaskEntity savedtaskEntity = taskRepository.save(taskEntity);


        AddTaskResponse addTaskResponse = modelMapper.map(savedtaskEntity, AddTaskResponse.class);

        return addTaskResponse;
    }

    @Override
    public GetAllTaskResponse getAllTasks() {

        List<TaskEntity> taskEntities = taskRepository.findAll();

        List<TaskDTO> taskDTOList = new ArrayList<>();


        for (TaskEntity taskEntity : taskEntities) {
            TaskDTO taskDTO = modelMapper.map(taskEntity, TaskDTO.class);
            taskDTOList.add(taskDTO);
        }

        GetAllTaskResponse getAllTaskResponse = new GetAllTaskResponse(taskDTOList);
        return getAllTaskResponse;
    }

    @Override
    public TaskResponse getTaskById(Long id) {


        Optional<TaskEntity> task = taskRepository.findById(id);


        if (!task.isPresent()) {
            try {
                throw new TaskNotFoundException("Task not found");
            } catch (TaskNotFoundException e) {
                throw new RuntimeException(e);
            }

        }


        return TaskResponse.builder()
                .task(modelMapper.map(task, TaskDTO.class))
                .build();
    }

    @Override
    public TaskResponse updateTask(Long taskId, AddTaskRequest addTaskRequest) {

        Optional<TaskEntity> task = taskRepository.findById(taskId);

        if (!task.isPresent()) {
            try {
                throw new TaskNotFoundException("Task not found");
            } catch (TaskNotFoundException e) {
                throw new RuntimeException(e);
            }

        }


        TaskEntity taskEntity = task.get();
        taskEntity.setTitle(addTaskRequest.getTitle());
        taskEntity.setDescription(addTaskRequest.getDescription());
        taskEntity.setStatus(addTaskRequest.getStatus());
        taskEntity.setPriority(addTaskRequest.getPriority());
        taskEntity.setDueDate(addTaskRequest.getDueDate());
        taskEntity.setCreatedAt(LocalDateTime.now());
        taskEntity.setUpdatedAt(LocalDateTime.now());


        TaskEntity savedtaskEntity = taskRepository.save(taskEntity);

        TaskResponse taskResponse = modelMapper.map(savedtaskEntity, TaskResponse.class);

        return taskResponse;


    }

    @Override
    public TaskResponse assignTask(Long taskId, Long userId) {

        TaskEntity task = null;
        try {
            task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        } catch (TaskNotFoundException e) {
            throw new RuntimeException(e);
        }


        UserEntity user = null;
        try {
            user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }

        task.setUser(user);

        task = taskRepository.save(task);
        TaskResponse taskResponse = modelMapper.map(task, TaskResponse.class);
        return taskResponse;

    }


    @Override
    public void deleteTaskById(Long id) {
        Optional<TaskEntity> taskEntity = taskRepository.findById(id);

        if (!taskEntity.isPresent()) {
            try {
                throw new TaskNotFoundException("Task not found");
            } catch (TaskNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        taskRepository.deleteById(id);
    }

    @Override
    public GetAllTaskResponse getAllTasksByUserId(Long userId) {



        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<TaskEntity> taskEntities = taskRepository.findAllTasksByUserId(userId);

        List<TaskDTO> taskDTOList = new ArrayList<>();


        for (TaskEntity taskEntity : taskEntities) {
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setId(taskEntity.getId());
            taskDTO.setTitle(taskEntity.getTitle());
            taskDTO.setDescription(taskEntity.getDescription());
            taskDTO.setStatus(taskEntity.getStatus());
            taskDTO.setPriority(taskEntity.getPriority());
            taskDTO.setDueDate(taskEntity.getDueDate());
            taskDTO.setCreatedAt(taskEntity.getCreatedAt());
            taskDTO.setUpdatedAt(taskEntity.getUpdatedAt());
            taskDTOList.add(taskDTO);
        }

// Create a GetAllTaskResponse object with the list of TaskDTOs
        GetAllTaskResponse getAllTaskResponse = new GetAllTaskResponse(taskDTOList);

// Return the response
        return getAllTaskResponse;

    }

    @Override
    public GetAllTaskResponse getAllTasksBySearch(String search) {
        List<TaskEntity> taskEntities = taskRepository.findByTitleContainingIgnoreCase(search);

        List<TaskDTO> taskDTOList = new ArrayList<>();
        GetAllTaskResponse getAllTaskResponse = new GetAllTaskResponse(taskDTOList);


        if (!taskEntities.isEmpty()) {
            return getGetAllTaskResponse(taskEntities, taskDTOList, getAllTaskResponse);
        }

        taskEntities = taskRepository.findByDescriptionContainingIgnoreCase(search);


        return getGetAllTaskResponse(taskEntities, taskDTOList, getAllTaskResponse);
    }


    private GetAllTaskResponse getGetAllTaskResponse(List<TaskEntity> taskEntities, List<TaskDTO> taskDTOList, GetAllTaskResponse getAllTaskResponse) {
        for (TaskEntity taskEntity : taskEntities) {
            TaskDTO taskDTO = modelMapper.map(taskEntity, TaskDTO.class);
            taskDTOList.add(taskDTO);
        }
        return getAllTaskResponse;
    }
}
