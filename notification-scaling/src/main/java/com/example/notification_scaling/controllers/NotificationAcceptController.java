package com.example.notification_scaling.controllers;

import com.example.notification_scaling.dto.NotificationRequest;
import com.example.notification_scaling.model.Notification;
import com.example.notification_scaling.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationAcceptController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/newNotification")
    public void postNewNotification(@RequestBody NotificationRequest notificationRequest) {
        System.out.println("Received the notification");
        Notification notification = new Notification();
        notification.setMessage(notificationRequest.getNotification());
        notificationService.newNotification(notification);
    }

}
