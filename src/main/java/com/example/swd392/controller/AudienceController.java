package com.example.swd392.controller;

import com.example.swd392.Request.CartRequest.AddToCartRequest;
import com.example.swd392.Request.CommentRequest.CreateCommentRequest;
import com.example.swd392.Request.FollowerRequest.CreateFollowerRequest;

import com.example.swd392.Request.LikeRequest.CreateLikeRequest;

import com.example.swd392.Request.OrderDetailRequest.CreateOrderDetailRequest;
import com.example.swd392.Request.OrderRequest.CreateOrderRequest;
import com.example.swd392.Request.PackageUser.createPackageUserRequest;
import com.example.swd392.Request.PreorderRequestRequest.CreatePreorderRequestRequest;
import com.example.swd392.Request.PreorderResponseRequest.CreatePreorderResponseRequest;
import com.example.swd392.Request.UserRequest.UpdateUserRequest;
import com.example.swd392.Response.CartResponse.CartResponse;
import com.example.swd392.Response.FollowerResponse.*;
import com.example.swd392.Response.LikeResponse.CreateLikeResponse;
import com.example.swd392.Response.LikeResponse.DeleteLikeResponse;
import com.example.swd392.Response.LikeResponse.FindLikeResponse;
import com.example.swd392.Response.LikeResponse.ListLikeResponse;
import com.example.swd392.Response.ObjectResponse.ResponseObject;


import com.example.swd392.Response.OrderDetailResponse.CreateOrderDetailResponse;
import com.example.swd392.Response.OrderResponse.CreateOrderResponse;
import com.example.swd392.Response.PackageUserResponse.CreatePackageUserResponse;
import com.example.swd392.Response.PreorderRequestResponse.CreatePreorderRequestResponse;
import com.example.swd392.Response.PreorderRequestResponse.DeletePreorderRequestResponse;
import com.example.swd392.Response.PreorderRequestResponse.FindPreorderRequestResponse;
import com.example.swd392.Response.PreorderRequestResponse.ListPreorderRequestResponse;
import com.example.swd392.Response.PreorderResponseResponse.CreatePreorderResponseResponse;
import com.example.swd392.Response.UserResponse.ChangeAvatarResponse;
import com.example.swd392.Response.UserResponse.UpdateUserResponse;
import com.example.swd392.model.*;
import com.example.swd392.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    @Autowired
    private ArtworkService artworkService;
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private PreorderRequestService preorderRequestService;
    @Autowired
    private  PreorderResponseService preorderResponseService;

    @Autowired
    private  CommentService commentService;

    @Autowired
    private PackageUserService packageUserService;

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

    @PostMapping("/createComment")
    @PreAuthorize("hasAuthority('audience:create')")
    public ResponseEntity<ResponseObject> createComment(@RequestBody CreateCommentRequest createCommentRequest) {
        return commentService.createComment(createCommentRequest);

    }

    @PutMapping("/updateComment/{commentId}")
    public ResponseEntity<ResponseObject> updateComment(
            @PathVariable Integer commentId,
            @RequestBody CreateCommentRequest createCommentRequest) {
        return commentService.updateComment(commentId, createCommentRequest);

    }

    @DeleteMapping("/deleteComment/{commentId}")
    public ResponseEntity<ResponseObject> deleteComment(@PathVariable Integer commentId) {
        return commentService.deleteComment(commentId);

    }

    @GetMapping("/findComment/{commentId}")
    public ResponseEntity<ResponseObject> findCommentById(@PathVariable Integer commentId) {
        return commentService.findCommentById(commentId);

    }

    @GetMapping("/getallComment")
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comments/searchFilter")
    public ResponseEntity<?> searchCommentsFilter(
            @RequestParam(name = "commentText", required = false) String commentText,
            @RequestParam(name = "commentedAt", required = false) Date commentedAt
    ) {
        List<Comment> commentList = commentService.searchCommentsFilter(commentText, commentedAt);
        return ResponseEntity.ok(commentList);
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
    @PostMapping("/Like")
    @PreAuthorize("hasAuthority('audience:create')")
    public CreateLikeResponse createLike(@RequestBody CreateLikeRequest likeRequest) {
        return likeService.Like(likeRequest);
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

    @GetMapping("/getallLikePagenable")
    public ResponseEntity<ListLikeResponse> findAllLikesPagenable(@RequestParam("page") Optional<Integer> page,
                                                         @RequestParam("size") Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(1) - 1, size.orElse(9));
        return likeService.findAllLikesPageNable(pageable);
    }


    @PostMapping("/createCreatePreorderRequestResponse")
    public ResponseEntity<CreatePreorderRequestResponse> createPreorderRequest(@RequestBody CreatePreorderRequestRequest request) {
        return preorderRequestService.createPreorderRequest(request);
    }

    @PutMapping("/updateCreatePreorderRequestResponse/{preorderRequestId}")
    public ResponseEntity<CreatePreorderRequestResponse> updatePreorderRequest(
            @PathVariable int preorderRequestId,
            @RequestBody CreatePreorderRequestRequest request) {
        return preorderRequestService.updatePreorderRequest(preorderRequestId, request);
    }

    @DeleteMapping("/deleteCreatePreorderRequestResponse/{preorderRequestId}")
    public ResponseEntity<DeletePreorderRequestResponse> deletePreorderRequest(@PathVariable int preorderRequestId) {
        return preorderRequestService.deletePreorderRequest(preorderRequestId);
    }

    @GetMapping("/findCreatePreorderRequestResponse/{preorderRequestId}")
    public ResponseEntity<FindPreorderRequestResponse> findPreorderRequestById(@PathVariable int preorderRequestId) {
        return preorderRequestService.findPreorderRequestById(preorderRequestId);
    }

    @GetMapping("CreatePreorderRequestResponse/list")
    public ResponseEntity<ListPreorderRequestResponse> findAllPreorderRequests() {
        return preorderRequestService.findAllPreorderRequests();
    }

//    @GetMapping("/search")
//    public ResponseEntity<?> searchPreorderRequests(
//            @RequestParam(name = "creatorId", required = false) Integer creatorId,
//            @RequestParam(name = "audienceId", required = false) Integer audienceId
//    ) {
//        return ResponseEntity.ok(preorderRequestService.searchPreorderRequests(creatorId, audienceId));
//    }

    @PostMapping("/CreatePreorderResponseResponse")
    public ResponseEntity<CreatePreorderResponseResponse> createPreorderResponse(@RequestBody CreatePreorderResponseRequest request) {
        return preorderResponseService.createPreorderResponse(request);
    }
    @PostMapping("/CreatePakageUser")
    public ResponseEntity<CreatePackageUserResponse> createPackageUser(@RequestBody createPackageUserRequest request) {
        return packageUserService.createPackageUser(request);

    }
    @GetMapping("/getAllArtWork")
    public ResponseEntity<List<Artwork>> getAllArtwork() {
        List<Artwork> artworks = artworkService.getAllArtworks();
        return ResponseEntity.ok(artworks);
    }

    @PostMapping("/createOrder")
    @PreAuthorize("hasAuthority('audience:create')")
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        CreateOrderResponse response = orderService.createOrder(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/createOrderDetail")
    @PreAuthorize("hasAuthority('audience:create')")
    public ResponseEntity<CreateOrderDetailResponse> createOrderDetail(@RequestBody CreateOrderDetailRequest request) {
        CreateOrderDetailResponse response = orderDetailService.createOrderDetail(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/order/{audienceId}")
    @PreAuthorize("hasAuthority('audience:read')")
    public List<Order> getOrdersByAudience(@PathVariable int audienceId) {
        User audience = iUserService.getUserById(audienceId);
        if (audience != null) {
            return orderService.getOrdersByAudience(audience);
        } else {
            return null;
        }
    }
    @GetMapping("/orderDetail/{orderId}")
    @PreAuthorize("hasAuthority('audience:read')")
    public List<OrderDetail> getOrderDetailsByOrderId(@PathVariable int orderId) {
        return orderDetailService.getOrderDetailsByOrderId(orderId);
    }
    @GetMapping("/artworks/{creatorId}")
    @PreAuthorize("hasAuthority('audience:read')")
    public ResponseEntity<List<Artwork>> getArtworksByCreatorId(@PathVariable int creatorId) {
        List<Artwork> artworks = artworkService.getArtWorkByCreatorId(creatorId);
        if (artworks != null && !artworks.isEmpty()) {
            return ResponseEntity.ok(artworks);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
