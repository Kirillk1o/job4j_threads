package ru.job4j.executerserv;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    /*
    метод должен через ExecutorService отправлять почту.
     */
    public void emailTo(User user) {
        pool.submit(() -> {
            String subject = "Notification " + user.getUserName() + " to email" + user.getEmail();
            String body = "Add a new event to" + user.getUserName();
            send(subject, body, user.getEmail());
        });
    }

    /*
    метод для закрытия pool
     */
    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {
    }
}
