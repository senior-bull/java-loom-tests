package com.bull.samples.loom.structured;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;

record StockTip(String symbol, double sentiment, double delta24) {}

public class SimpleStructuredConcurrencyExample {

    private static double getSentiment(String sumbol) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return 5.0;
    }

    private static double getDelta24(String sumbol) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return 5.0;
    }

    public static StockTip calc(String symbol) {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Callable<Double> getSentiment = () -> getSentiment(symbol);
            Subtask<Double> fSentiment = scope.fork(getSentiment);

            Callable<Double> getDelta = () -> getDelta24(symbol);
            Subtask<Double> fDelta = scope.fork(getDelta);

            scope.join();
            scope.throwIfFailed();

            return new StockTip(symbol, fSentiment.get(), fDelta.get());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException("Some requests failed", e);
        }
    }

    public static void main(String[] args) {
        System.out.println(calc("GOOG"));
    }
}
