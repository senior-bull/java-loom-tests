package com.bull.samples.loom;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestVirtualThread {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<?> submit = executorService.submit(() -> System.out.println(Thread.currentThread().getName() + ": " + "This is a test"));
            submit.get();
        }
    }
}
