package com.bull.samples.loom.performance;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class MassiveThreadExample {

    public static void main(String[] args) {
        AtomicInteger ctr = new AtomicInteger(0);
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 10_000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    ctr.incrementAndGet();
                    return i;
                });
            });
        }

        System.out.println(ctr.get());
    }
}
