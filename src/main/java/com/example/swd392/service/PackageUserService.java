package com.example.swd392.service;

import com.example.swd392.Request.PackageUser.createPackageUserRequest;
import com.example.swd392.Response.PackageUserResponse.CreatePackageUserResponse;
import org.springframework.http.ResponseEntity;

public interface PackageUserService {
    ResponseEntity<CreatePackageUserResponse> createPackageUser(createPackageUserRequest request);
}
