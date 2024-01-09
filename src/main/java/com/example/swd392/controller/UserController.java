package com.example.swd392.controller;

import com.example.swd392.model.User;
import com.example.swd392.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Test")
public class UserController {

    @Autowired
    private UserService iUserService;
    @GetMapping("/list")
    public List<User> getAllRole(){
        return iUserService.getAll();
    }

    @GetMapping("/test")
    private ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello");
    }
}
