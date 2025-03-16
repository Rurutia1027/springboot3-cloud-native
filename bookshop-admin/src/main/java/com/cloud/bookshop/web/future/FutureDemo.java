package com.cloud.bookshop.web.future;

import java.util.concurrent.*;

public class FutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(FutureDemo::doSomeLongOperation);
        System.out.println("start TS " + System.currentTimeMillis());
        String ret = future.get(10, TimeUnit.SECONDS);
        System.out.println("end TS " + System.currentTimeMillis());
        System.out.println("future ret value is " + ret);


    }

    // time-consuming operation
    public static String doSomeLongOperation() throws InterruptedException {
        Thread.sleep(2000);
        return "123";
    }
}
