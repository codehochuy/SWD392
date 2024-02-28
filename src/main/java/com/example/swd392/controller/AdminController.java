package com.example.swd392.controller;

import com.example.swd392.Request.UserRequest.SearchRequest;
import com.example.swd392.Response.ObjectResponse.ResponseObject;
import com.example.swd392.Response.UserResponse.ResponseUser;
import com.example.swd392.Response.UserResponse.UpdateUserResponse;
import com.example.swd392.model.User;
import com.example.swd392.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private UserService iUserService;
    @PutMapping("/ban/{email}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<UpdateUserResponse> banUser(@PathVariable String email) {
        UpdateUserResponse response = iUserService.banUser(email);
        if (response.getUser() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/unban/{email}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<UpdateUserResponse> unbanUser(@PathVariable String email) {
        UpdateUserResponse response = iUserService.unbanUser(email);
        if (response.getUser() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @GetMapping("/creators")
    public List<User> getCreators() {
        return iUserService.getCreator();
    }
    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public String get() {
        return "GET:: admin controller";
    }
    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public String post() {
        return "POST:: admin controller";
    }
    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    public String put() {
        return "PUT:: admin controller";
    }

    @GetMapping("/getUsers")
    public ResponseEntity<ResponseUser> getAllUsers(@RequestBody SearchRequest req){
        return iUserService.searchUsers(req);
    }

    @GetMapping("/findCreatorById")
    public ResponseEntity<ResponseObject> getUserById(){
        return iUserService.findAllCreator();
    }
}
