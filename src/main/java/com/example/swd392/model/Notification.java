package com.example.swd392.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NotificationID")
    private int notificationId;

    @ManyToOne
    @JoinColumn(name = "SenderUserID")
    private User senderUser;

    @ManyToOne
    @JoinColumn(name = "ReceiverUserID")
    private User receiverUser;

    @Column(name = "NotificationType", length = 50, nullable = false)
    private String notificationType;

    @Column(name = "NotificationDate", nullable = false)
    private LocalDateTime notificationDate;
}
