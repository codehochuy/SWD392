package com.example.swd392.repository;

import com.example.swd392.model.User;
import com.example.swd392.service.UserService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface
UserRepo extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUsersID(int userid);

    Optional<User> findByUsersID(Integer userid);
}
