package com.bull.samples.loom.structured;

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
            Subtask<Double> fSentiment = scope.fork(() -> getSentiment(symbol));
            Subtask<Double> fDelta = scope.fork(() -> getDelta24(symbol));

            return new StockTip(symbol, fSentiment.get(), fDelta.get());
        } catch (Exception e) {
            throw new RuntimeException("Some requests failed", e);
        }
    }

    public static void main(String[] args) {
        System.out.println(calc("GOOG"));
    }
}
