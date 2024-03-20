package com.example.swd392.service;

import com.example.swd392.Request.UserRequest.ChangeAvatarRequest;
import com.example.swd392.Request.UserRequest.CreatUserRequest;
import com.example.swd392.Request.UserRequest.SearchRequest;
import com.example.swd392.Request.UserRequest.UpdateUserRequest;
import com.example.swd392.Response.ObjectResponse.ResponseObject;
import com.example.swd392.Response.UserResponse.*;
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
    public UpdateUserResponse banUserByID(int userID);
    public UpdateUserResponse unbanUser(String email);
    public UpdateUserResponse unbanUserByID(int userID);

    public List<User> getCreator();

    ResponseEntity<ResponseUser> searchUsers(SearchRequest req);


    ResponseEntity<ResponseObject> findAllCreator();

    ResponseEntity<ResponseObject> findUserById(int userId);

    ResponseEntity<ResponseObject> findAllUsers();

    List<User> searchUser(String searchValue, String phone);

    User findByEmailForMail(String email);

    User saveUserForMail(User user);

    User getUserById(int audienceId);

    UpdateUserResponse updateAccountBalance(int userid ,BalanceRequest request);
    UpdateUserResponse ReduceAccountBalance(int userid ,BalanceRequest request);
}
