package com.example.notification_scaling.services;

import com.example.notification_scaling.model.Notification;

public class NotificationWaiter implements INotificationWaiter {

    private Long startTime;
    private final Thread thread;

    private Notification notification;

    public NotificationWaiter(Thread thread) {
        this.setStartTime(System.currentTimeMillis());
        this.thread = thread;
        System.out.println("Waiter is waiting on Thread: " + thread.threadId());
    }

    public void waitOnThread() {
        synchronized (this.thread) {
            try {
                this.thread.wait(200000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    @Override
    public Long getStartTime() {
        return this.startTime;
    }

    @Override
    public void deliverNotification(Notification notification) {
        if (notification == null) return;
        this.notification = notification;
        synchronized (this.thread) {
            this.thread.notify();
        }
    }

    public Notification getNotification() {
        return this.notification;
    }
}
