package com.example.swd392.controller;

import com.example.swd392.Request.UserRequest.CreatUserRequest;
import com.example.swd392.Response.UserResponse.CreateUserResponse;
import com.example.swd392.Response.UserResponse.RegisterResponse;
import com.example.swd392.auth.AuthenticationRequest;
import com.example.swd392.auth.AuthenticationResponse;
import com.example.swd392.auth.AuthenticationService;
import com.example.swd392.auth.RegisterRequest;
import com.example.swd392.config.LogoutService;
import com.example.swd392.model.User;
import com.example.swd392.service.EmailService;
import com.example.swd392.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final LogoutService logoutService;
    private final UserService userService;
    private  final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.login(authenticationRequest));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request,response);

    }

    @PostMapping("/createUser")
    public ResponseEntity<CreateUserResponse> register(@RequestBody CreatUserRequest request) throws IOException {
        return ResponseEntity.ok(authenticationService.createUser(request));
    }

//
//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
//        logoutService.logout(request, response, null);
//        return ResponseEntity.ok("Logout successfully");
//    }

    @PostMapping("/forgetpass")
    public ResponseEntity<?> register(@RequestParam String email) {
        try {
            User user = userService.findByEmailForMail(email);
            if(user == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
            }
            else {
                String pass = RandomStringUtils.randomAlphanumeric(6);

                user.setPassword(passwordEncoder.encode(pass));
                user = userService.saveUserForMail(user);
//                emailService.sendSimpleMessage(email,"Reset password","New password is : " + pass);
                emailService.sendSimpleMessage(email,"WARNING","EMAIL Của Bạn Phát Hiện Xâm Nhập Lạ : " );
                return ResponseEntity.ok(user);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(RegisterResponse
                    .builder()
                    .status(e.getMessage())
                    .message("Register fail")
                    .build());
        }


    }
}
