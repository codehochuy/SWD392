package com.example.swd392.Response.PackageResponse;

import com.example.swd392.model.Package;
import com.example.swd392.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PackageResponse {
    private String status;
    private String message;
    private List<Package> packages;
}
