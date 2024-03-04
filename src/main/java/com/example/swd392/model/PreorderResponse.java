package com.example.swd392.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "PreorderResponse")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreorderResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PreorderResponseID")
    private int preorderResponseId;

    @ManyToOne
    @JoinColumn(name = "PreorderRequestID")
    private PreorderRequest preorderRequest;

    @Column(name = "Price")
    private double price;

    @Column(name = "Description", length = 50, nullable = false)
    private String description;

    @Column(name = "TimeResponse", nullable = false)
    private Date timeResponse;
}
