package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.lastName = :lastName")
    List<User> findUsersByLastName(@Param("lastName") String lastName); // JPQL query

    // Removed duplicate method definition


    List<User> findByLastName(String lastName); // Existing method


    
    List<User> findByName(String name);


}
