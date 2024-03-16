package com.example.swd392.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Package")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PackageID")
    private int packageID;

    @Column(name = "PackageName")
    private String packageName;

    @Column(name = "PackagePrice")
    private double packagePrice;

    @Column(name = "PackageTime")
    private int packageTime;


}
