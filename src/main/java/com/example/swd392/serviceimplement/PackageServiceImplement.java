package com.example.swd392.serviceimplement;

import com.example.swd392.Request.PackageRequest.CreatePackageRequest;
import com.example.swd392.Request.PackageRequest.UpdatePackageRequest;
import com.example.swd392.Response.PackageResponse.PackageResponse;
import com.example.swd392.model.Package;
import com.example.swd392.repository.PackageRepo;
import com.example.swd392.service.PackageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PackageServiceImplement implements PackageService {
    @Autowired
    private PackageRepo packageRepo;

    @Override
    public ResponseEntity<PackageResponse> getAll() {
        List<Package> packages = packageRepo.findAll();
        PackageResponse response = new PackageResponse();
        response.setStatus("success");
        response.setMessage("Packages retrieved successfully");
        response.setPackages(packages);

        return ResponseEntity.ok(response);
    }


    @Override
    public ResponseEntity<PackageResponse> deletePackage(int packageId) {
        // Check if the package exists
        Optional<Package> optionalPackage = packageRepo.findById(packageId);
        if (optionalPackage.isPresent()) {
            // Package found, delete it
            packageRepo.deleteById(packageId);

            // Prepare the response
            PackageResponse response = PackageResponse.builder()
                    .status("Success")
                    .message("Package deleted successfully")
                    .build();

            return ResponseEntity.ok(response);
        } else {
            // Package not found, return an appropriate error response
            PackageResponse response = PackageResponse.builder()
                    .status("Error")
                    .message("Package not found")
                    .build();

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @Override
    public Optional<Package> findById(int packageId) {
        return packageRepo.findById(packageId);
    }

    @Override
    public ResponseEntity<PackageResponse> update(int packageId, UpdatePackageRequest request) {
        try {
            // Check if the package with the given ID exists
            Optional<Package> optionalPackage = packageRepo.findById(packageId);
            if (optionalPackage.isPresent()) {
                Package existingPackage = optionalPackage.get();

                // Validate the updated package details
                if (StringUtils.isBlank(request.getPackageName()) || request.getPackageName().length() <= 10) {
                    return ResponseEntity.badRequest()
                            .body(PackageResponse.builder()
                                    .status("Error")
                                    .message("Package Name must be provided and greater than 10 characters")
                                    .packages(Collections.emptyList())
                                    .build());
                }

                if (request.getPackagePrice() <= 0) {
                    return ResponseEntity.badRequest()
                            .body(PackageResponse.builder()
                                    .status("Error")
                                    .message("Package Price must be greater than 0")
                                    .packages(Collections.emptyList())
                                    .build());
                }

                if (request.getPackageTime() <= 0) {
                    return ResponseEntity.badRequest()
                            .body(PackageResponse.builder()
                                    .status("Error")
                                    .message("Package Time must be greater than 0")
                                    .packages(Collections.emptyList())
                                    .build());
                }

                // Update package details based on the request
                existingPackage.setPackageName(request.getPackageName());
                existingPackage.setPackagePrice(request.getPackagePrice());
                existingPackage.setPackageTime(request.getPackageTime());

                // Save the updated package
                Package updatedPackage = packageRepo.save(existingPackage);

                // Prepare the response
                PackageResponse response = PackageResponse.builder()
                        .status("Success")
                        .message("Package updated successfully")
                        .packages(Collections.singletonList(updatedPackage))
                        .build();

                return ResponseEntity.ok(response);
            } else {
                // If the package with the given ID is not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(PackageResponse.builder()
                                .status("Error")
                                .message("Package not found with ID: " + packageId)
                                .packages(Collections.emptyList())
                                .build());
            }
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(PackageResponse.builder()
                            .status("Error")
                            .message("Internal server error")
                            .packages(Collections.emptyList())
                            .build());
        }
    }


    @Override
    public ResponseEntity<PackageResponse> createPackage(CreatePackageRequest request) {
        try {
            // Check if packageName is null or empty
            if (StringUtils.isBlank(request.getPackageName())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(PackageResponse.builder()
                                .status("Error")
                                .message("Package Name cannot be null or empty")
                                .packages(Collections.emptyList())
                                .build());
            }

            // Check if packagePrice is null
            if (request.getPackagePrice() == null || request.getPackagePrice() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(PackageResponse.builder()
                                .status("Error")
                                .message("Package Price must be provided and greater than 0")
                                .packages(Collections.emptyList())
                                .build());
            }

            if (request.getPackageTime() == null || request.getPackageTime() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(PackageResponse.builder()
                                .status("Error")
                                .message("Package Time must be provided and greater than 0")
                                .packages(Collections.emptyList())
                                .build());
            }




            // Map the DTO to your entity
            Package newPackage = Package.builder()
                    .packageName(request.getPackageName())
                    .packagePrice(request.getPackagePrice())
                    .packageTime(request.getPackageTime())
                    .build();
            // Map other fields...

            // Save the package
            Package savedPackage = packageRepo.save(newPackage);

            // Prepare the response
            PackageResponse response = PackageResponse.builder()
                    .status("Success")
                    .message("Package created successfully")
                    .packages(Collections.singletonList(savedPackage))
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(PackageResponse.builder()
                            .status("Error")
                            .message("Internal server error")
                            .packages(Collections.emptyList())
                            .build());
        }
    }


}


