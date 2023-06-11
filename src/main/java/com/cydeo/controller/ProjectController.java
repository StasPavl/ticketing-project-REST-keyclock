package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.UserDTO;
import com.cydeo.service.ProjectService;
import com.cydeo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {
    private final ProjectService projectService;
    private final UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getProjects(){
        List<ProjectDTO> listOfProjects = projectService.listAllProjects();
        return ResponseEntity.ok(new ResponseWrapper("List of projects retrieved successfully", listOfProjects, HttpStatus.OK));
    }
    @GetMapping("/{code}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getProjectByCode(@PathVariable String code){
        ProjectDTO project = projectService.getByProjectCode(code);
        return ResponseEntity.ok(new ResponseWrapper("Project retrieved successfully", project, HttpStatus.OK));
    }
    @PostMapping
    @RolesAllowed({"Manager", "Admin"})
    public ResponseEntity<ResponseWrapper> createProject(@RequestBody ProjectDTO project){
        projectService.save(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Projected created",HttpStatus.OK));
    }
    @PutMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> updateProject(@RequestBody ProjectDTO project){
        projectService.update(project);
        return ResponseEntity.ok(new ResponseWrapper("Project updated", HttpStatus.OK));
    }
    @DeleteMapping("/{code}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> deleteProject(@PathVariable String code){
        projectService.delete(code);
        return ResponseEntity.ok(new ResponseWrapper("Project deleted", HttpStatus.OK));
    }
    @GetMapping("/manager/project-status")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getProjectByManager(){
        List<ProjectDTO> listOfProjects = projectService.listAllProjectDetails();
        return ResponseEntity.ok(new ResponseWrapper("Projects are successfully retrieved",listOfProjects, HttpStatus.OK));
    }
    @PutMapping("/manage/complete{code}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> managerCompleteProject(@PathVariable String code){
        projectService.complete(code);
        return ResponseEntity.ok(new ResponseWrapper("Project completed",HttpStatus.OK));
    }
}
