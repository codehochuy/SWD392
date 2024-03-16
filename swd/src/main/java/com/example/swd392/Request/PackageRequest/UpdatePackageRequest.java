package com.example.swd392.Request.PackageRequest;

public class UpdatePackageRequest {
    private String packageName;
    private double packagePrice;
    private int packageTime;

    // Add the getter for packageName
    public String getPackageName() {
        return packageName;
    }
    public double getPackagePrice() {
        return packagePrice;
    }

    public int getPackageTime(){
        return packageTime;
    }
}
