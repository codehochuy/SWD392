package com.example.swd392.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(name = "PackageUserID")
    private int packageUserId;

    @Column(name = "StartDate")
    private LocalDateTime startDate;

    @Column(name = "Enddate")
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "PackageID")
    private Package aPackage;
}
