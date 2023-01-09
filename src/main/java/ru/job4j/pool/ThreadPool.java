package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final int size = Runtime.getRuntime().availableProcessors();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    /*
    этот метод должен добавлять задачи в блокирующую очередь tasks.
     */
    public void work(Runnable job) {
       try {
           tasks.offer(job);
           if (threads.size() < size) {
               threads.add(new Thread(tasks.poll()));
           }
           for (Thread tmp : threads) {
               tmp.start();
           }
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
    }

    /*
    этот метод завершит все запущенные задачи.
     */
    public void shutdown() {
        for (Thread tmp : threads) {
            tmp.interrupt();
        }
    }
}
