package com.project.userService.service;

import com.project.userService.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User createUser(User user);
    List<User> getAllUser();
    User getUserById(UUID id);
    User updateUser(UUID id , User user);
    void deleteUser(UUID id);

}
