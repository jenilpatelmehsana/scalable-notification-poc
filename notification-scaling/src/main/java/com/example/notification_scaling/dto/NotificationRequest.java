package com.example.notification_scaling.dto;

public class NotificationRequest {

    private String notification;

    public NotificationRequest() {
    }

    public NotificationRequest(String notification) {
        this.notification = notification;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }
}
