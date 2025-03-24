package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entity.User;

public interface UserService {

    Page<User> getAllUsers(Pageable pageable);

    Optional<User> getUserById(Long id);

    User createUser(User user);

    User updateUser(Long id, User userDetails);

    void deleteUser(Long id);

    List<User> findByLastName(String lastName);
}
