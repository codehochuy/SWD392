package com.example.swd392.service;
import com.example.swd392.Request.PackageRequest.CreatePackageRequest;
import com.example.swd392.Request.PackageRequest.UpdatePackageRequest;
import com.example.swd392.Response.ObjectResponse.ResponseObject;
import com.example.swd392.Response.PackageResponse.PackageResponse;
import com.example.swd392.model.Package;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;


public interface PackageService {
    ResponseEntity<PackageResponse> getAll();
    ResponseEntity<PackageResponse> createPackage(CreatePackageRequest request);
    ResponseEntity<PackageResponse> deletePackage(int packageId);
    Optional<Package> findById(int packageId);
    ResponseEntity<PackageResponse> update(int packageId, UpdatePackageRequest request);
}
