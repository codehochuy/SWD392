package com.example.swd392.controller;

import com.example.swd392.Request.UserRequest.CreatUserRequest;
import com.example.swd392.Response.OrderDetailResponse.OrderDetailResponse;
import com.example.swd392.Response.UserResponse.CreateUserResponse;
import com.example.swd392.Response.UserResponse.RegisterResponse;
import com.example.swd392.auth.AuthenticationRequest;
import com.example.swd392.auth.AuthenticationResponse;
import com.example.swd392.auth.AuthenticationService;
import com.example.swd392.auth.RegisterRequest;
import com.example.swd392.config.LogoutService;
import com.example.swd392.model.Artwork;
import com.example.swd392.model.OrderDetail;
import com.example.swd392.model.User;
import com.example.swd392.service.ArtworkService;
import com.example.swd392.service.EmailService;
import com.example.swd392.service.OrderDetailService;
import com.example.swd392.service.UserService;
import com.sun.nio.sctp.NotificationHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.WebSocketSession;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final LogoutService logoutService;
    private final UserService userService;
    private  final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private  final ArtworkService artworkService;
    @Autowired
    private OrderDetailService orderDetailService;

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
                emailService.sendSimpleMessage(email,"Reset password","New password is : " + pass);
//                emailService.sendSimpleMessage(email,"WARNING","EMAIL Của Bạn Phát Hiện Xâm Nhập Lạ : " );
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
    @GetMapping("/random")
    public List<Artwork> getRandomArtworks() {
        int count = 100; // Set the count of artworks you want to fetch
        return artworkService.getListArtworkForGuest(count);
    }
    @PostMapping("/sendArtworkInfo/{orderId}")
    public ResponseEntity<?> sendArtworkInfo(@PathVariable int orderId) {
        try {
            // Gọi dịch vụ để lấy thông tin chi tiết đơn hàng
            ResponseEntity<OrderDetailResponse> response = orderDetailService.getAllOrderDetails(orderId);

            if (response.getStatusCode() == HttpStatus.OK) {
                OrderDetailResponse orderDetailResponse = response.getBody();

                // Kiểm tra xem danh sách chi tiết đơn hàng có rỗng không
                if (orderDetailResponse != null && !orderDetailResponse.getOrderDetails().isEmpty()) {
                    StringBuilder emailContentBuilder = new StringBuilder();

                    for (OrderDetail orderDetail : orderDetailResponse.getOrderDetails()) {
                        String artworkUrl = orderDetail.getArtwork().getArtworkUrl();
                        String audienceEmail = orderDetail.getOrder().getAudience().getEmail();

                        // Thêm thông tin chi tiết đơn hàng vào nội dung email
                        emailContentBuilder.append("Artwork URL for order ").append(orderId).append(": ").append(artworkUrl).append("\n");

                        // Gửi email cho mỗi đơn hàng
                        emailService.sendSimpleMessage(audienceEmail, "Artwork Information", emailContentBuilder.toString());
                    }

                    return ResponseEntity.ok("Emails sent successfully");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data received from the API");
                }
            } else {
                return ResponseEntity.status(response.getStatusCode()).body("Failed to fetch order details");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

}
