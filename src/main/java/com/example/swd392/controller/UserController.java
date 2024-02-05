package com.example.swd392.controller;

import com.example.swd392.Request.UserRequest.UpdateUserRequest;
import com.example.swd392.Response.UserResponse.UpdateUserResponse;
import com.example.swd392.model.User;
import com.example.swd392.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService iUserService;

    @GetMapping("/list")
    public List<User> getAllRole() {
        return iUserService.getAll();
    }

    @GetMapping("/test")
    private ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello");
    }

    @PostMapping("/updateUser/{email}")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @PathVariable String email,
            @RequestBody UpdateUserRequest updateUserRequest) {
        UpdateUserResponse response = iUserService.updateUser(email, updateUserRequest);
        return ResponseEntity.ok(iUserService.updateUser(email, updateUserRequest));
    }


}
