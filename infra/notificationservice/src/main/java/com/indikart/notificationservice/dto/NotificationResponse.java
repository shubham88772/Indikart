package com.indikart.notificationservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationResponse {

    private Long id;
    private Long userId;
    private String email;
    private String message;
    private String type;
    private LocalDateTime createdAt;
}