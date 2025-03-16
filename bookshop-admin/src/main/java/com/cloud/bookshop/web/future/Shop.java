package com.cloud.bookshop.web.future;

import lombok.Data;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Data
public class Shop {
    private String name;

    public Shop(String name) {
        this.name = name;
    }

    Random random = new Random();

    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public double getPrice(String product) {
        delay();
        return random.nextDouble() * 100;
    }

    public Future<Double> getPriceAsync(String product) {
        // method-1: this is not recommended
//        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
//        new Thread(() -> futurePrice.complete(getPrice(product))).start();
//        return futurePrice;

        // method-2:
        // here we use CompletableFuture#supplyAsync converted the
        // sync method into the async method
        return CompletableFuture.supplyAsync(() -> getPrice(product));
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Shop shop = new Shop("one shop");
        long start = System.currentTimeMillis();
        Future<Double> futurePrice = shop.getPriceAsync("some product");
        System.out.println("invoke response time-consuming: " + (System.currentTimeMillis() - start));
        double price = futurePrice.get();
        System.out.println("price invoke response time-consuming: " + (System.currentTimeMillis() - start));

    }
}
