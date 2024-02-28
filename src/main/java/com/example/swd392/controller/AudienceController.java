package com.example.swd392.controller;

import com.example.swd392.Request.CartRequest.AddToCartRequest;
import com.example.swd392.Request.UserRequest.UpdateUserRequest;
import com.example.swd392.Response.CartResponse.CartResponse;
import com.example.swd392.Response.ObjectResponse.ResponseObject;
import com.example.swd392.Response.UserResponse.ChangeAvatarResponse;
import com.example.swd392.Response.UserResponse.UpdateUserResponse;
import com.example.swd392.model.Cart;
import com.example.swd392.model.User;
import com.example.swd392.service.CartService;
import com.example.swd392.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@PreAuthorize("hasRole('AUDIENCE')")
public class AudienceController {

    @Autowired
    private UserService iUserService;

    @Autowired
    private CartService iCartService;

    @GetMapping("/list")
//    @PreAuthorize("hasAuthority('audience:read')")
    @PreAuthorize("hasAnyAuthority('audience:read','creator:read')")
    public List<User> getAllUser() {
        return iUserService.getAll();
    }

    @GetMapping("/test")
    @PreAuthorize("hasAuthority('audience:read')")
    private ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello");
    }

    @PutMapping("/updateUser/{email}")
    @PreAuthorize("hasAuthority('audience:update')")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @PathVariable String email,
            @RequestBody UpdateUserRequest updateUserRequest) {
//        UpdateUserResponse response = iUserService.updateUser(email, updateUserRequest);
        return ResponseEntity.ok(iUserService.updateUser(email, updateUserRequest));
    }
    @PostMapping("/avatar/{email}")
    @PreAuthorize("hasAuthority('audience:create')")
    public ResponseEntity<ChangeAvatarResponse> changeAvatar(
            @PathVariable String email,
            @RequestParam("image") MultipartFile file) throws IOException {
        ChangeAvatarResponse changeAvatar = iUserService.changeAvatar(email, file);
        return ResponseEntity.ok(changeAvatar);
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasAuthority('audience:read')")
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

    @PostMapping("/addToCart")
    @PreAuthorize("hasAuthority('audience:buy')")
    public CartResponse addToCart(@RequestBody AddToCartRequest request) {
        return iCartService.addToCart(request);
    }

    @DeleteMapping("/remove-from-cart/{cartId}")
    @PreAuthorize("hasAnyAuthority('audience:delete')")
    public CartResponse removeFromCart(@PathVariable int cartId) {
        return iCartService.removeCart(cartId);
    }

    @GetMapping("/view-cart/{userId}")
    @PreAuthorize("hasAuthority('audience:read')")
    public Object viewCartByUserId(@PathVariable int userId) {
        List<Cart> cartList = iCartService.viewCartByUserId(userId);
        if (cartList != null) {
            // Trả về danh sách mục trong giỏ hàng
            return cartList;
        } else {
            // Trả về thông báo khi không tìm thấy người dùng
            return "User not found";
        }
    }

    @GetMapping("/findUser/{userId}")
    public ResponseEntity<ResponseObject> findUserById(@PathVariable int userId) {
        return iUserService.findUserById(userId);
    }

    @GetMapping("/getAllUsers")
    private ResponseEntity<ResponseObject> getAllUsers() {
        return iUserService.findAllUsers();
    }



}
