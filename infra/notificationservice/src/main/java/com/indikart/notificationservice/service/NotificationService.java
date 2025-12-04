package com.indikart.notificationservice.service;

import com.indikart.notificationservice.dto.NotificationRequest;
import com.indikart.notificationservice.dto.NotificationResponse;

public interface NotificationService {

    NotificationResponse sendNotification(NotificationRequest request);
}