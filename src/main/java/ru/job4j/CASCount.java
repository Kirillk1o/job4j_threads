package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();
    AtomicInteger atomicIntegeric = new AtomicInteger();
    private volatile int value = count.get();
    private volatile int next;

    public void increment() {
        do {
            next = value++;
        } while (!count.compareAndSet(value, next));
    }

    public int get() {
        if (value == 0) {
            throw new UnsupportedOperationException("Count is not impl.");
        }
        return value;
    }
}
