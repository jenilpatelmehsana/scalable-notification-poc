package com.example.notification_scaling.controllers;

import com.example.notification_scaling.dto.NotificationRequest;
import com.example.notification_scaling.dto.NotificationResponse;
import com.example.notification_scaling.model.Notification;
import com.example.notification_scaling.services.NotificationService;
import com.example.notification_scaling.services.NotificationWaiter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class NotificationDeliveryController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/generic")
    public void genericNotification(HttpServletResponse response) throws IOException {
        NotificationWaiter notificationWaiter = new NotificationWaiter(Thread.currentThread());
        notificationService.registerWaiter(notificationWaiter);
        notificationWaiter.waitOnThread();
        if (notificationWaiter.getNotification() != null) {
            response.setContentType("application/json");
            NotificationResponse nr = new NotificationResponse();
            nr.setNotification(notificationWaiter.getNotification().getMessage());
            response.getWriter().write(nr.toString());
        } else {
            response.setStatus(404);
        }
    }

}
