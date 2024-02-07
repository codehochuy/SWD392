package com.example.swd392.controller;

import com.example.swd392.Request.UserRequest.UpdateUserRequest;
import com.example.swd392.Response.UserResponse.ChangeAvatarResponse;
import com.example.swd392.Response.UserResponse.UpdateUserResponse;
import com.example.swd392.model.User;
import com.example.swd392.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
//        UpdateUserResponse response = iUserService.updateUser(email, updateUserRequest);
        return ResponseEntity.ok(iUserService.updateUser(email, updateUserRequest));
    }
    @PostMapping("/avatar/{email}")
    public ResponseEntity<ChangeAvatarResponse> changeAvatar(
            @PathVariable String email,
            @RequestParam("image") MultipartFile file) throws IOException {
        ChangeAvatarResponse changeAvatar = iUserService.changeAvatar(email, file);
        return ResponseEntity.ok(changeAvatar);
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> downloadImage(@PathVariable String email){
        byte[] imageData = iUserService.downloadImage(email);
        if(imageData == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Avatar not found" + email);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @PostMapping("/ban/{email}")
    public ResponseEntity<UpdateUserResponse> banUser(@PathVariable String email) {
        UpdateUserResponse response = iUserService.banUser(email);
        if (response.getUser() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/unban/{email}")
    public ResponseEntity<UpdateUserResponse> unbanUser(@PathVariable String email) {
        UpdateUserResponse response = iUserService.unbanUser(email);
        if (response.getUser() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }



}
