package com.example.swd392.serviceimplement;

import com.example.swd392.Request.UserRequest.CreatUserRequest;
import com.example.swd392.Request.UserRequest.SearchRequest;
import com.example.swd392.Request.UserRequest.UpdateUserRequest;
import com.example.swd392.Response.ObjectResponse.ResponseObject;
import com.example.swd392.Response.UserResponse.*;
import com.example.swd392.Util.ImageUtil;
import com.example.swd392.auth.AuthenticationResponse;
import com.example.swd392.auth.RegisterRequest;
import com.example.swd392.enums.Role;
import com.example.swd392.model.User;
import com.example.swd392.repository.UserRepo;
import com.example.swd392.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserImplement implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAll() {
        List<User> allUsers = userRepo.findAll();
        return allUsers.stream()
                .filter(user -> user.getRole() == Role.AUDIENCE || user.getRole() == Role.CREATOR)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getCreator() {
        Role creatorRole = Role.CREATOR;
        return userRepo.findByRole(creatorRole);
    }

    @Override
    public ResponseEntity<ResponseUser> searchUsers(SearchRequest req) {
        try {
            List<User> userList = userRepo.findAll();
            if(req.getUserId() != null){
                userList = userList.stream().filter(n -> n.getUsersID() == req.getUserId()).toList();
            }
            if(req.getName() != null && !req.getName().trim().isEmpty()){
                userList = userList.stream().filter(n -> n.getUsername().contains(req.getName())).toList();
            }
            if(req.getEmail() != null && !req.getEmail().trim().isEmpty()){
                userList = userList.stream().filter(n -> n.getEmail().contains(req.getEmail())).toList();
            }
            return ResponseEntity.ok(new ResponseUser("Success","List users", userList));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseUser.builder()
                    .status("Fail")
                    .message("List user fail")
                    .userList(null)
                    .build());
        }
    }

    @Override
    public ResponseEntity<ResponseObject> findAllCreator() {
        var CreatorsAll = userRepo.findAll();
        return ResponseEntity.ok().body(new ResponseObject("Success","List Creators",CreatorsAll));
    }

    @Override
    public ResponseEntity<ResponseObject> findUserById(int userId) {
        try {
            User User = userRepo.findById(userId).orElse(null);
            if (User == null) {
                // Xử lý trường hợp không tìm thấy blog
            }
            return ResponseEntity.ok(new ResponseObject("Success", "Find blog success", User));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("Fail", "Internal Server Error", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> findAllUsers() {
        var UserAll = userRepo.findAll();
        return ResponseEntity.ok().body(new ResponseObject("Success","List blog",UserAll));
    }

    @Override
    public List<User> searchUser(String searchValue, String phone) {
        return userRepo.findUsersByFilter(searchValue, phone);
    }

    @Override
    public User findByEmailForMail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    @Override
    public User saveUserForMail(User user) {
        return userRepo.save(user);
    }

    @Override
    public User getUserById(int audienceId) {
        Optional<User> userOptional = userRepo.findById(audienceId);
        return userOptional.orElse(null);
    }

    @Override
    public UpdateUserResponse updateAccountBalance(int userid,BalanceRequest request) {
        double money = request.getMoney();
        var user = userRepo.findByUsersID(userid).orElse(null);
        if(user != null){
            user.setAccountBalance(user.getAccountBalance()+ money);
            userRepo.save(user);
            return UpdateUserResponse.builder()
                    .status("Update Account balance successful")
                    .user(user)
                    .build();
        }else
        {
            return UpdateUserResponse.builder()
                    .status("User not found")
                    .user(null)
                    .build();
        }
    }

    @Override
    public UpdateUserResponse ReduceAccountBalance(int userid, BalanceRequest request) {
        double money = request.getMoney();
        var user = userRepo.findByUsersID(userid).orElse(null);
        if(user != null){
            user.setAccountBalance(user.getAccountBalance()- money);
            userRepo.save(user);
            return UpdateUserResponse.builder()
                    .status("Update Account balance successful")
                    .user(user)
                    .build();
        }else
        {
            return UpdateUserResponse.builder()
                    .status("User not found")
                    .user(null)
                    .build();
        }
    }

    @Override
    public BalanceAccountResponse getBalanceAccount(int userid) {
        var user = userRepo.findByUsersID(userid).orElse(null);
        if(user != null &&(user.getRole() == Role.AUDIENCE || user.getRole() == Role.CREATOR)){
            return BalanceAccountResponse.builder()
                    .status("Your balance is :")
                    .money(user.getAccountBalance())
                    .build();
        }
        else {
            return BalanceAccountResponse.builder()
                    .status("User not found")
                    .build();
        }
    }

    @Override
    public UserInfoResponse getUserInfo(int userid) {
        var user = userRepo.findByUsersID(userid).orElse(null);
        if(user != null){
            return UserInfoResponse.builder()
                    .status("Your information")
                    .user(user)
                    .build();
        }
        else {
            return UserInfoResponse.builder()
                    .status("User not found")
                    .user(null)
                    .build();
        }
    }


    @Override
    public UpdateUserResponse updateUser(String email, UpdateUserRequest updateUserRequest) {
        var existedUser = userRepo.findUserByEmail(email).orElse(null);
        if (existedUser != null) {
            existedUser.setAccountName(updateUserRequest.getName());
            existedUser.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
            existedUser.setPhone(updateUserRequest.getPhone());
            userRepo.save(existedUser);
            return UpdateUserResponse.builder()
                    .status("Update User Successful")
                    .user(existedUser)
                    .build();
        } else {
            return UpdateUserResponse.builder()
                    .status("User Not Found")
                    .user(null)
                    .build();

        }
    }

    @Override
    public ChangeAvatarResponse changeAvatar(String email, MultipartFile file) throws IOException {
        var user = userRepo.findUserByEmail(email).orElse(null);
        if (user != null) {
            user.setAvatar(ImageUtil.compressImage(file.getBytes()));
            userRepo.save(user);
            return ChangeAvatarResponse.builder()
                    .status("Update Avatar Successful")
                    .user(getUserInfo(email))
                    .build();
        } else {
            return ChangeAvatarResponse.builder()
                    .status("User Not Found")
                    .user(null)
                    .build();
        }
    }

    @Override
    public byte[] downloadImage(String email) {
        User user = userRepo.findUserByEmail(email).orElse(null);
        if (user == null || user.getAvatar() == null) {
            return null;
        }
        return ImageUtil.decompressImage(user.getAvatar());
    }

    @Override
    public UpdateUserResponse banUser(String email) {
        var banUser = userRepo.findUserByEmail(email).orElse(null);
//        var banUser = userRepo.findUserByUsersID(userID).orElse(null);
        if (banUser != null) {
            banUser.setUserStatus(false);
            userRepo.save(banUser);
            return UpdateUserResponse.builder()
                    .status("Ban User Successful")
                    .user(banUser)
                    .build();
        } else {
            return UpdateUserResponse.builder()
                    .status("User Not Found")
                    .user(null)
                    .build();

        }
    }

    @Override
    public UpdateUserResponse banUserByID(int userID) {
        var banUser = userRepo.findUserByUsersID(userID).orElse(null);
//        var banUser = userRepo.findUserByUsersID(userID).orElse(null);
        if (banUser != null) {
            banUser.setUserStatus(false);
            userRepo.save(banUser);
            return UpdateUserResponse.builder()
                    .status("Ban User Successful")
                    .user(banUser)
                    .build();
        } else {
            return UpdateUserResponse.builder()
                    .status("User Not Found")
                    .user(null)
                    .build();

        }
    }

    @Override
    public UpdateUserResponse unbanUser(String email) {
        var unbanUser = userRepo.findUserByEmail(email).orElse(null);
//        var unbanUser = userRepo.findUserByUsersID(userID).orElse(null);
        if (unbanUser != null) {
            unbanUser.setUserStatus(true);
            userRepo.save(unbanUser);
            return UpdateUserResponse.builder()
                    .status("UnBan User Successful")
                    .user(unbanUser)
                    .build();
        } else {
            return UpdateUserResponse.builder()
                    .status("User Not Found")
                    .user(null)
                    .build();

        }
    }

    @Override
    public UpdateUserResponse unbanUserByID(int userID) {
        var unbanUser = userRepo.findUserByUsersID(userID).orElse(null);
//        var unbanUser = userRepo.findUserByUsersID(userID).orElse(null);
        if (unbanUser != null) {
            unbanUser.setUserStatus(true);
            userRepo.save(unbanUser);
            return UpdateUserResponse.builder()
                    .status("UnBan User Successful")
                    .user(unbanUser)
                    .build();
        } else {
            return UpdateUserResponse.builder()
                    .status("User Not Found")
                    .user(null)
                    .build();

        }
    }

    public User getUserInfo(String email) {
        User user = userRepo.findUserByEmail(email).orElse(null);
        if (user != null) {
            byte[] compressedImage = user.getAvatar();
            if (compressedImage != null && compressedImage.length > 0) {
                byte[] decompressedImage = ImageUtil.decompressImage(compressedImage);
                user.setAvatar(decompressedImage);
            }
            return user;
        }
        return null;
    }


}
