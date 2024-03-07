package com.example.swd392.Request.PackageUser;

import lombok.Data;

import java.time.LocalDateTime;
@Data

public class createPackageUserRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int userId;
    private int packageId;

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }
}
