package com.example.swd392.Request.PackageRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePackageRequest {
    private String packageName;
    private Double packagePrice;
    private Integer packageTime;
}
