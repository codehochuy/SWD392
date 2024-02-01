package com.example.swd392.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "UserReport")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserReportID")
    private int userReportId;

    @Column(name = "Description", length = 50)
    private String description;

    @ManyToOne
    @JoinColumn(name = "ArtworkID")
    private Artwork artwork;

    @ManyToOne
    @JoinColumn(name = "UsersID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ReportID")
    private Report report;
}
