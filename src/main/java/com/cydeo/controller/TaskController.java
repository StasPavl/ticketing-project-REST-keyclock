package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.TaskDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    @GetMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getTasks(){
        List<TaskDTO> listOfTasks = taskService.listAllTasks();
        return ResponseEntity.ok(new ResponseWrapper("All tasks retrieved", listOfTasks, HttpStatus.OK));
    }
    @GetMapping("/{id}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getTaskById(@PathVariable Long id){
        TaskDTO task = taskService.findById(id);
        return ResponseEntity.ok(new ResponseWrapper("Task retrieved", task, HttpStatus.OK));
    }
    @PostMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> createTask(@RequestBody TaskDTO task){
        taskService.save(task);
        return ResponseEntity.ok(new ResponseWrapper("Task Created",HttpStatus.CREATED));
    }
    @DeleteMapping("/{id}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> deleteTask(@PathVariable Long id){
        taskService.delete(id);
        return ResponseEntity.ok(new ResponseWrapper("Task deleted" ,HttpStatus.OK));
    }
    @PutMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> updateTask(@RequestBody TaskDTO task){
        taskService.update(task);
        return ResponseEntity.ok(new ResponseWrapper("Task updated", HttpStatus.OK));
    }
    @GetMapping("/employee/pending-task")
    @RolesAllowed("Employee")
    public ResponseEntity<ResponseWrapper> employeePendingTasks(){
        List<TaskDTO> taskDTOS = taskService.listAllTasksByStatusIsNot(Status.COMPLETE);
        return ResponseEntity.ok(new ResponseWrapper("List of task with in progress status", taskDTOS,HttpStatus.OK));
    }
    @PutMapping("/employee/update")
    @RolesAllowed("Employee")
    public ResponseEntity<ResponseWrapper> employeeUpdateTasks(@RequestBody TaskDTO task){
        taskService.update(task);
        return ResponseEntity.ok(new ResponseWrapper("Task updated", HttpStatus.OK));
    }
    @GetMapping("/employee/archive")
    @RolesAllowed("Employee")
    public ResponseEntity<ResponseWrapper> employeeArchivedTasks(){
        List<TaskDTO> taskDTOS = taskService.listAllTasksByStatus(Status.COMPLETE);
        return ResponseEntity.ok(new ResponseWrapper("List of all completed tasks", taskDTOS, HttpStatus.OK));
    }

}
