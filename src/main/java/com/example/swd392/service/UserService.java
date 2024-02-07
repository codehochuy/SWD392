package com.example.swd392.service;

import com.example.swd392.Request.UserRequest.ChangeAvatarRequest;
import com.example.swd392.Request.UserRequest.UpdateUserRequest;
import com.example.swd392.Response.UserResponse.ChangeAvatarResponse;
import com.example.swd392.Response.UserResponse.UpdateUserResponse;
import com.example.swd392.auth.RegisterRequest;
import com.example.swd392.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    public List<User> getAll();

    public User register(MultipartFile multipartFile, RegisterRequest request) throws IOException;

    public UpdateUserResponse updateUser(String email, UpdateUserRequest updateUserRequest);
    public ChangeAvatarResponse changeAvatar(String email, MultipartFile file) throws IOException;

    public byte[] downloadImage(String fileName);

    public UpdateUserResponse banUser(String email);
    public UpdateUserResponse unbanUser(String email);
}
