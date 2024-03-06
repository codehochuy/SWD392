package com.example.swd392.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "PackageUser")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PackageUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PackageUserID")
    private Integer packageUserId;

    @Column(name = "StartDate")
    private LocalDateTime startDate;

    @Column(name = "Enddate")
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "UsersID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "PackageID")
    private Package aPackage;
}
