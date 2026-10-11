package com.project.userService.repository;

import com.project.userService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface userRepository extends JpaRepository<User, UUID> {
}
