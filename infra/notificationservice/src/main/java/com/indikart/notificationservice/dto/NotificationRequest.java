package com.indikart.notificationservice.dto;

import lombok.Data;

@Data
public class NotificationRequest {

    private Long userId;     // user who will receive the notification
    private String email;    // receiver email
    private String message;  // content of the notification
    private String type;     // EMAIL / SMS / PUSH
}