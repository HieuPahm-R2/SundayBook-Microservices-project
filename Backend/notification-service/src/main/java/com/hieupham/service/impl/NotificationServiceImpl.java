package com.hieupham.service.impl;

import com.hieupham.mapper.NotificationMapper;
import com.hieupham.modal.Notification;
import com.hieupham.payload.dto.NotificationDTO;
import com.hieupham.repository.NotificationRepository;
import com.hieupham.service.NotificationService;
import com.hieupham.service.client.BookingFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final BookingFeignClient bookingFeignClient;
    @Override
    public NotificationDTO createNotification(Notification notification) {
        return null;
    }

    @Override
    public List<Notification> getAllNotificationsByUserId(Long userId) {
        return List.of();
    }

    @Override
    public List<Notification> getAllNotificationsBySalonId(Long salonId) {
        return List.of();
    }

    @Override
    public Notification markNotificationAsRead(Long notificationId) throws Exception {
        return null;
    }

    @Override
    public void deleteNotification(Long notificationId) {

    }

    @Override
    public List<Notification> getAllNotifications() {
        return List.of();
    }
}
