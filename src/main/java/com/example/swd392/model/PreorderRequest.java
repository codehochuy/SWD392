package com.example.swd392.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "PreorderRequest")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreorderRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PreorderRequestID")
    private int preorderRequestId;

    @ManyToOne
    @JoinColumn(name = "CreatorID")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "AudienceID")
    private User audience;

    @Column(name = "Description", length = 50, nullable = false)
    private String description;

    @Column(name = "OrderPlacedAt", nullable = false)
    private Date orderPlacedAt;
}
