package com.example.swd392.repository;

import com.example.swd392.enums.Role;
import com.example.swd392.model.User;
import com.example.swd392.service.UserService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface
UserRepo extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findUserByEmail(String email);

    List<User> findByRole(Role role);

    Optional<User> findUserByUsersID(int userid);

    Optional<User> findByUsersID(Integer userid);
}
