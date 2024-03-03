package com.example.swd392.controller;

import com.example.swd392.Request.CartRequest.AddToCartRequest;
import com.example.swd392.Request.FollowerRequest.CreateFollowerRequest;
import com.example.swd392.Request.FollowerRequest.UpdateFollowerRequest;
import com.example.swd392.Request.LikeRequest.CreateLikeRequest;
import com.example.swd392.Request.LikeRequest.DeleteLikeRequest;
import com.example.swd392.Request.UserRequest.UpdateUserRequest;
import com.example.swd392.Response.CartResponse.CartResponse;
import com.example.swd392.Response.FollowerResponse.*;
import com.example.swd392.Response.LikeResponse.CreateLikeResponse;
import com.example.swd392.Response.LikeResponse.DeleteLikeResponse;
import com.example.swd392.Response.LikeResponse.FindLikeResponse;
import com.example.swd392.Response.LikeResponse.ListLikeResponse;
import com.example.swd392.Response.ObjectResponse.ResponseObject;
import com.example.swd392.Response.UserResponse.ChangeAvatarResponse;
import com.example.swd392.Response.UserResponse.UpdateUserResponse;
import com.example.swd392.model.Cart;
import com.example.swd392.model.Follower;
import com.example.swd392.model.User;
import com.example.swd392.service.CartService;
import com.example.swd392.service.FollowerService;
import com.example.swd392.service.LikeService;
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

    @Autowired
    private  FollowerService followerService;

    @Autowired
    private LikeService likeService;

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

    @GetMapping("/searchUser")
    public ResponseEntity<?> searchUser(
            @RequestParam(name = "searchValue", required = false) String searchValue,
            @RequestParam(name = "phone", required = false) String phone) {

        List<User> userList = iUserService.searchUser(searchValue, phone);
        return ResponseEntity.ok(userList);
    }


    @PostMapping("/createFollower")
    public ResponseEntity<CreateFollowerResponse> createFollower(@RequestBody CreateFollowerRequest followerRequest) {
        return followerService.createFollower(followerRequest);
    }


    @PutMapping("/updateFollower/{followerId}")
    public ResponseEntity<CreateFollowerResponse> updateFollower(
            @PathVariable int followerId,
            @RequestBody CreateFollowerRequest followerRequest) {
        return followerService.updateFollower(followerId, followerRequest);
    }

    @DeleteMapping("/deleteFollower/{followerId}")
    public ResponseEntity<DeleteFollowerResponse> deleteFollower(@PathVariable int followerId) {
        return followerService.deleteFollower(followerId);
    }

    @GetMapping("/findFolower/{followerId}")
    public ResponseEntity<FindFollowerResponse> findFollowerById(@PathVariable int followerId) {
        return followerService.findFollowerById(followerId);
    }

    @GetMapping("/getallFollower")
    public ResponseEntity<ListFollowerResponse> findAllFollowers() {
        return followerService.findAllFollowers();
    }

    @GetMapping("/followers/searchFollower")
    public ResponseEntity<?> searchFollowers(
            @RequestParam(name = "userId", required = false) Integer userId,
            @RequestParam(name = "accountName", required = false) String accountName
    ) {
        List<Follower> followerList = followerService.searchFollowers(userId, accountName);
        return ResponseEntity.ok(followerList);
    }
    @PostMapping("/createLike")
    public ResponseEntity<CreateLikeResponse> createLike(@RequestBody CreateLikeRequest likeRequest) {
        return likeService.createLike(likeRequest);
    }
    @DeleteMapping("/deleteLike/{likeId}")
    public ResponseEntity<DeleteLikeResponse> deleteLike(@PathVariable int likeId) {
        return likeService.deleteLike(likeId);
    }

    @GetMapping("/findLikeById/{likeId}")
    public ResponseEntity<FindLikeResponse> findLikeById(@PathVariable int likeId) {
        return likeService.findLikeById(likeId);
    }

    @GetMapping("/getallLike")
    public ResponseEntity<ListLikeResponse> findAllLikes() {
        return likeService.findAllLikes();
    }

}
