package com.hieupham.messaging;

import com.hieupham.modal.Notification;
import com.hieupham.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventConsumer {

    private final NotificationService notificationService;


    @RabbitListener(queues = "notification-queue")
    public void sentBookingUpdateEvent(Notification notification) {
        notificationService.createNotification(notification);
    }
}
