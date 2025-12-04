package com.indikart.notificationservice.service.impl;

import com.indikart.notificationservice.dto.NotificationRequest;
import com.indikart.notificationservice.dto.NotificationResponse;
import com.indikart.notificationservice.entity.Notification;
import com.indikart.notificationservice.repository.NotificationRepository;
import com.indikart.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;

    @Override
    @Transactional
    public NotificationResponse sendNotification(NotificationRequest request) {
        // 1. Build entity
        Notification notification = Notification.builder()
                .userId(request.getUserId())
                .email(request.getEmail())
                .message(request.getMessage())
                .type(request.getType())
                .createdAt(LocalDateTime.now())
                .build();

        // 2. Persist
        Notification saved = repository.save(notification);
        log.info("Saved notification id={} for user={}", saved.getId(), saved.getUserId());

        // 3. Deliver (simulate)
        try {
            deliver(saved);
            log.info("Delivered notification id={} to {}", saved.getId(), saved.getEmail());
        } catch (Exception ex) {
            // for now we just log; later we will implement retry/DLQ
            log.error("Failed to deliver notification id={} - will keep as persisted. reason={}", saved.getId(), ex.getMessage(), ex);
        }

        // 4. Map to response DTO and return
        NotificationResponse resp = new NotificationResponse();
        resp.setId(saved.getId());
        resp.setUserId(saved.getUserId());
        resp.setEmail(saved.getEmail());
        resp.setMessage(saved.getMessage());
        resp.setType(saved.getType());
        resp.setCreatedAt(saved.getCreatedAt());
        return resp;
    }

    /**
     * Simulated delivery — replace with real email provider later.
     * Keep this method simple so you can later extract to async worker.
     */
    private void deliver(Notification n) {
        // Simulate email send by logging. Replace with JavaMailSender or HTTP webhook later.
        log.info("SIMULATED SEND -> to={} subject='Notification for order' body='{}'", n.getEmail(), n.getMessage());

        // If you want to simulate failure during tests, uncomment this to throw:
        // if (n.getEmail().contains("fail")) throw new RuntimeException("simulated failure");
    }
}