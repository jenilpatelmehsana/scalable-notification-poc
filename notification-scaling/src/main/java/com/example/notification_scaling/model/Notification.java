package com.example.notification_scaling.model;

public class Notification {

    private String message;

    private Long userID = 0L;

    public Notification() {
    }

    public Notification(String message, Long userID) {
        this.message = message;
        this.userID = userID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }
}
