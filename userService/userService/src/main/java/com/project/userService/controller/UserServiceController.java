package com.project.userService.controller;


import com.project.userService.model.User;
import com.project.userService.service.UserService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserServiceController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUser());
    }
 @GetMapping("/{id}")
public ResponseEntity<User> getUserById(@Valid @PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
 }

 @PutMapping("/{id}")
 public ResponseEntity<User> updateUser( @PathVariable UUID id,@Valid @RequestBody User user
                                        ){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(id,user));
 }

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteUser(@PathVariable UUID id){
    userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
}
}
