package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {
    private final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);

    @Test
    void whenAddThenPush() {
        List<Integer> list = new LinkedList<>();
        AtomicInteger atomicInteger = new AtomicInteger();
        Thread producerThread = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        Thread consumerThread = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        int tmp = 0;
                        try {
                            tmp = queue.poll();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        atomicInteger.set(tmp);
                        list.add(tmp);
                    }
                }
        );
        producerThread.start();
        consumerThread.start();
        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertThat(atomicInteger.get()).isEqualTo(4);
        assertThat(list).isEqualTo(List.of(0, 1, 2, 3, 4));
    }
}