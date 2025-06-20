package com.example.notification_scaling.services;

import com.example.notification_scaling.model.Notification;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;

@Service
public class NotificationService {

    private static final Long NO_RESPONSE_DELAY = 180000L;  // 3 MINs

    private ArrayBlockingQueue<Notification> notifications;

    private ArrayBlockingQueue<INotificationWaiter> waiters;

    private final Thread waiterProcessThread = new Thread(() -> {
        while(true) {
            INotificationWaiter waiter = this.waiters.peek();
            if (waiter == null) {
                sleep();
                continue;
            }
            Notification newNotification = this.notifications.poll();
            Long currentTime = System.currentTimeMillis();
            if (newNotification != null) { // deliver the notification
                this.waiters.parallelStream().forEach(w -> {
                    if (w != null && w.getStartTime() - currentTime < NO_RESPONSE_DELAY) {
                        w.deliverNotification(newNotification);
                    }
                });
                this.waiters.clear();
//                while(!this.waiters.isEmpty()) {
//
//                }
            } else {    // send back all the waiters waiting for notification empty handed
                if (currentTime - waiter.getStartTime() < NO_RESPONSE_DELAY) {  // find all
                    while (!this.waiters.isEmpty()) {
                        INotificationWaiter rejectWaiter = this.waiters.peek();
                        if (currentTime - rejectWaiter.getStartTime() > NO_RESPONSE_DELAY) {
                            rejectWaiter = this.waiters.poll();
                            if (rejectWaiter != null) rejectWaiter.deliverNotification(null);
                        } else {
                            break;
                        }
                    }
                } else {
                    sleep();
                }
            }
        }
    });

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public NotificationService() {
        this.notifications = new ArrayBlockingQueue<>(10);
        this.waiters = new ArrayBlockingQueue<>(1000000);
        waiterProcessThread.start();
    }

    public void registerWaiter(INotificationWaiter waiter) {
        this.waiters.offer(waiter);
    }

    public void newNotification(Notification notification) {
        this.notifications.offer(notification);
    }
}
