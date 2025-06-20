package com.example.notification_scaling.services;

import com.example.notification_scaling.model.Notification;

public interface INotificationWaiter {
    public void setStartTime(Long startTime);

    public Long getStartTime();

    public void deliverNotification(Notification notification);
}
