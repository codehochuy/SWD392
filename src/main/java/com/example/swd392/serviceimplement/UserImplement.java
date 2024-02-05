package com.example.swd392.serviceimplement;

import com.example.swd392.Request.UserRequest.UpdateUserRequest;
import com.example.swd392.Response.UserResponse.UpdateUserResponse;
import com.example.swd392.Util.ImageUtil;
import com.example.swd392.auth.RegisterRequest;
import com.example.swd392.enums.Role;
import com.example.swd392.model.User;
import com.example.swd392.repository.UserRepo;
import com.example.swd392.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserImplement implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public User register(MultipartFile multipartFile, RegisterRequest request) throws IOException {
        return userRepo.save(User.builder()
                .accountName(request.getName())
                .email(request.getEmail())
                .avatar(ImageUtil.compressImage(multipartFile.getBytes()))
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .userStatus(true)
                .role(Role.GUEST).build());

    }

    @Override
    public UpdateUserResponse updateUser(String email, UpdateUserRequest updateUserRequest) {
        var existedUser = userRepo.findUserByEmail(email).orElse(null);
        if(existedUser!= null){
            existedUser.setAccountName(updateUserRequest.getName());
            existedUser.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
            existedUser.setPhone(updateUserRequest.getPhone());
            userRepo.save(existedUser);
            User user = userRepo.findUserByEmail(email).orElse(null);
            return UpdateUserResponse.builder()
                    .status("Update User Successful")
                    .user(user)
                    .build();
        }
        else{
            return UpdateUserResponse.builder()
                    .status("User Not Found")
                    .user(null)
                    .build();

        }
    }

    @Override
    public User changeAvatar(int userId, MultipartFile file) {
        return null;
    }


}
