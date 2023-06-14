package com.cydeo.controller;

import com.cydeo.annotation.DefaultExceptionMessage;
import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.UserDTO;
import com.cydeo.exception.TicketingProjectException;
import com.cydeo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "UserController", description = "User API")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    @RolesAllowed({"Manager","Admin"})
    @Operation(summary = "Get users")
    public ResponseEntity<ResponseWrapper> getUsers(){
        List<UserDTO> listDTO = userService.listAllUsers();
        return ResponseEntity.ok(
                new ResponseWrapper("Users retrieved successful", listDTO, HttpStatus.OK));
    }
    @GetMapping("/{username}")
    @RolesAllowed({"Admin"})
    @Operation(summary = "Get users by username")
    public ResponseEntity<ResponseWrapper> getUserByUserName(@PathVariable String username){
        UserDTO user = userService.findByUserName(username);
        return ResponseEntity.ok(new ResponseWrapper("user found", user,HttpStatus.FOUND));

    }
    @PostMapping
    @RolesAllowed({"Admin"})
    @Operation(summary = "Create user")
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO userDto){
        userService.save(userDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("User created", HttpStatus.OK));
    }
    @PutMapping
    @RolesAllowed({"Admin"})
    @Operation(summary = "Update user")
    public ResponseEntity<ResponseWrapper> updateUser(@RequestBody UserDTO userDTO){
        userService.update(userDTO);
        return ResponseEntity.ok(new ResponseWrapper("user updated", HttpStatus.OK));
    }
    @DeleteMapping("/{username}")
    @RolesAllowed({"Admin"})
    @Operation(summary = "Delete user")
    @DefaultExceptionMessage(defaultMessage = "Failed to delete user")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable String username) throws TicketingProjectException {
        userService.delete(username);
        return ResponseEntity.ok(new ResponseWrapper("user deleted", HttpStatus.OK));
    }
}
