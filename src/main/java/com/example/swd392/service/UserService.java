package com.example.swd392.service;

import com.example.swd392.Request.UserRequest.ChangeAvatarRequest;
import com.example.swd392.Request.UserRequest.CreatUserRequest;
import com.example.swd392.Request.UserRequest.SearchRequest;
import com.example.swd392.Request.UserRequest.UpdateUserRequest;
import com.example.swd392.Response.ObjectResponse.ResponseObject;
import com.example.swd392.Response.UserResponse.ChangeAvatarResponse;
import com.example.swd392.Response.UserResponse.CreateUserResponse;
import com.example.swd392.Response.UserResponse.ResponseUser;
import com.example.swd392.Response.UserResponse.UpdateUserResponse;
import com.example.swd392.auth.RegisterRequest;
import com.example.swd392.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<User> getAll();

    public UpdateUserResponse updateUser(String email, UpdateUserRequest updateUserRequest);
    public ChangeAvatarResponse changeAvatar(String email, MultipartFile file) throws IOException;

    public byte[] downloadImage(String fileName);

    public UpdateUserResponse banUser(String email);
    public UpdateUserResponse unbanUser(String email);

    public List<User> getCreator();

    ResponseEntity<ResponseUser> searchUsers(SearchRequest req);


    ResponseEntity<ResponseObject> findAllCreator();

    ResponseEntity<ResponseObject> findUserById(int userId);

    ResponseEntity<ResponseObject> findAllUsers();

    List<User> searchUser(String searchValue, String phone);

    User findByEmailForMail(String email);

    User saveUserForMail(User user);

    User getUserById(int audienceId);
}
