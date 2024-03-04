package com.example.swd392.Response.PackageUserResponse;

import com.example.swd392.model.PackageUser;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePackageUserResponse {
    private String status;
    private String message;
    private PackageUser packageUser;
}