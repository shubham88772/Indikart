package com.indikart.notificationservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;       // who receives the notification

    private String email;      // recipient email

    private String message;    // email or app message content

    private String type;       // EMAIL / SMS / PUSH etc.

    private LocalDateTime createdAt;
}