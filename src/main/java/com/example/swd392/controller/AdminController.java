package com.example.swd392.controller;

import com.example.swd392.Request.PackageRequest.CreatePackageRequest;
import com.example.swd392.Request.PackageRequest.UpdatePackageRequest;
import com.example.swd392.Request.UserRequest.SearchRequest;
import com.example.swd392.Response.ObjectResponse.ResponseObject;
import com.example.swd392.Response.OrderDetailResponse.OrderDetailResponse;
import com.example.swd392.Response.OrderResponse.OrderResponse;
import com.example.swd392.Response.UserResponse.BalanceRequest;
import com.example.swd392.Response.UserResponse.ResponseUser;
import com.example.swd392.Response.UserResponse.UpdateUserResponse;
import com.example.swd392.model.Package;
import com.example.swd392.model.User;
import com.example.swd392.service.OrderDetailService;
import com.example.swd392.service.OrderService;
import com.example.swd392.service.PackageService;
import com.example.swd392.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import com.example.swd392.Response.PackageResponse.PackageResponse;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private UserService iUserService;
    @Autowired
    private PackageService ipackageService;
    @Autowired
    private OrderService iorderService;
    @Autowired
    private OrderDetailService iorderDetailService;

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

//    @GetMapping
//    @PreAuthorize("hasAuthority('admin:read')")
//    public String get() {
//        return "GET:: admin controller";
//    }
//
//    @PostMapping
//    @PreAuthorize("hasAuthority('admin:create')")
//    public String post() {
//        return "POST:: admin controller";
//    }
//
//    @PutMapping
//    @PreAuthorize("hasAuthority('admin:update')")
//    public String put() {
//        return "PUT:: admin controller";
//    }

    @GetMapping("/getUsers")
    public ResponseEntity<ResponseUser> getAllUsers(@RequestBody SearchRequest req) {
        return iUserService.searchUsers(req);
    }

    @GetMapping("/findCreatorById")
    public ResponseEntity<ResponseObject> getUserById() {
        return iUserService.findAllCreator();
    }

    @GetMapping("/getAllPackages")
    public ResponseEntity<PackageResponse> getAllPackages() {
        return ipackageService.getAll();
    }

    @PostMapping("/createPackage")
    public ResponseEntity<PackageResponse> createPackage(@RequestBody CreatePackageRequest request) {
        return ipackageService.createPackage(request);
    }

    @DeleteMapping("/deletePackage/{packageId}")
    public ResponseEntity<PackageResponse> deletePackage(@PathVariable int packageId) {
        return ipackageService.deletePackage(packageId);
    }

    @PutMapping("/updatePackage/{packageId}")
    public ResponseEntity<PackageResponse> updatePackage(@PathVariable int packageId, @RequestBody UpdatePackageRequest request) {
        return ipackageService.update(packageId, request);
    }
    @GetMapping("/searchPackage")
    public ResponseEntity<PackageResponse> searchPackage(@RequestParam String packageName) {
        return ipackageService.searchPackage(packageName);
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<OrderResponse> getAllOrder() {return iorderService.getAll();
    }
    @GetMapping("/getAllOrderDetails")
    public ResponseEntity<OrderDetailResponse> getAllOrderDetails(@RequestParam int orderID) {
        return iorderDetailService.getAllOrderDetails(orderID);
    }

    @DeleteMapping("/deleteOrder/{orderId}")
    public ResponseEntity<OrderResponse> deleteOrder (@PathVariable int orderId) {
        return iorderService.deleteOrder(orderId);
    }

    @GetMapping("/searchOrders")
    public ResponseEntity<OrderResponse> searchOrders(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        return iorderService.searchOrders(fromDate, toDate);
    }

    @PutMapping("/{userId}/balance")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<UpdateUserResponse> updateAccountBalance(
            @PathVariable("userId") int userId,
            @RequestBody BalanceRequest request) {

        UpdateUserResponse response = iUserService.updateAccountBalance(userId, request);

        if (response.getUser() != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{userId}/reduce")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<UpdateUserResponse> ReduceAccountBalance(
            @PathVariable("userId") int userId,
            @RequestBody BalanceRequest request) {
        UpdateUserResponse response = iUserService.ReduceAccountBalance(userId, request);

        if (response.getUser() != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}





