package com.cloud.bookshop.web.future;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class PriceDemo {
    private List<Shop> shops = Lists.newArrayList(
            new Shop("shop1"),
            new Shop("shop2"),
            new Shop("shop3"),
            new Shop("shop4"),
            new Shop("shop5"),
            new Shop("shop6"),
            new Shop("shop7"),
            new Shop("shop8"),
            new Shop("shop9"),
            new Shop("shop10"),
            new Shop("shop11"),
            new Shop("shop12"),
            new Shop("shop13"),
            new Shop("shop14"),
            new Shop("shop15"),
            new Shop("shop16"),
            new Shop("shop17"),
            new Shop("shop18"),
            new Shop("shop19"),
            new Shop("shop20"),
            new Shop("shop21"),
            new Shop("shop22"),
            new Shop("shop23"),
            new Shop("shop24"),
            new Shop("shop25"));

    // this function:
    // receives a product name, return a list of string that includes the name of the shop
    // that sells the project, and the product's price in that shop.l

    // -- test env: macOS M1 with 12 cores -- in parallel mode 12 cores will create 12
    // threads to execute in parallel in default

    // time-consuming level: 25118:
    public List<String> findPrices_v1(String product) {
        return shops.stream().map(mapper -> String.format("%s price is %.2f",
                        mapper.getName(), mapper.getPrice(product)))
                .collect(Collectors.toList());
    }

    // time-consuming level: 3017,
    // conclusion: parallel streaming is suitable for handling compute dentensive scenarios
    // if there associates rpc/relay remote invokes, parallel streaming will cause
    // application behave low-effectively
    public List<String> findPrices_v2_parallel_stream(String product) {
        return shops.stream().parallel()
                .map(mapper -> String.format("%s price is %.2f",
                        mapper.getName(), mapper.getPrice(product)))
                .collect(Collectors.toList());
    }

    // time-consuming level: 3019ms
    public List<String> findPrices_v3_completable_future(String product) {
        List<CompletableFuture<String>> ret =
                shops.stream().map(shop -> CompletableFuture.supplyAsync(() -> String.format(
                                "%s price is %.2f", shop.getName(), shop.getPrice(product))))
                        .collect(Collectors.toList());
        // join is similar as Future#get, but join does not throw exception during block period
        return ret.stream().map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    // conclusion: most effective combination: thread-pool & completable
    // time-consuming: 1034ms
    public List<String> findPrices_v4_thread_pool(String product) {
        Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100));

        List<CompletableFuture<String>> ret = shops.stream()
                .map(shop -> CompletableFuture
                        .supplyAsync(() -> String.format("%s price is %.2f", shop.getName(),
                                shop.getPrice(product)), executor))
                .collect(Collectors.toList());
        return ret.stream().map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        PriceDemo priceDemo = new PriceDemo();
        long start = System.currentTimeMillis();
        System.out.println(priceDemo.findPrices_v1("iPhone7"));
        System.out.println("original time-consuming " + (System.currentTimeMillis() - start));


        // parallel time consuming
        start = System.currentTimeMillis();
        System.out.println(priceDemo.findPrices_v2_parallel_stream("iPhone8"));
        System.out.println("v2 parallel time-consuming " + (System.currentTimeMillis() - start));


        // completable future handle multi-threads
        start = System.currentTimeMillis();
        System.out.println(priceDemo.findPrices_v3_completable_future("macOS"));
        System.out.println("v3 multi-thread complete-able time-consuming " + (System.currentTimeMillis() - start));


        // completeable future handle multi-threads with thread pool
        start = System.currentTimeMillis();
        System.out.println(priceDemo.findPrices_v4_thread_pool(UUID.randomUUID().toString()));
        System.out.println("v4 multi-thread with thread local via completeable " +
                "time-consuming " + (System.currentTimeMillis() - start));
    }
}
