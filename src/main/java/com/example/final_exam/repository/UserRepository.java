package com.example.final_exam.repository;

import com.example.final_exam.model.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

}
