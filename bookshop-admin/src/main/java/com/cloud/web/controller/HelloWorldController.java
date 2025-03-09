package com.cloud.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {

    private ConcurrentMap<Long, DeferredResult<String>> map = new ConcurrentHashMap<>();

    @GetMapping("/exception")
    public String exception() {
        throw new RuntimeException("exception test endpoint");
    }

    @GetMapping("/callable")
    public Callable<String> helloCallable() {
        long startTs = new Date().getTime();
        System.out.println(Thread.currentThread().getName() + " start");
        Callable<String> result = () -> {
            System.out.println("start " + System.currentTimeMillis());
            Thread.sleep(1000L);
            System.out.println("end " + System.currentTimeMillis());
            return "Hello-" + UUID.randomUUID().toString();
        };

        System.out.println(Thread.currentThread().getName() + " cost TS " + (new Date().getTime() - startTs));
        return result;
    }

    @GetMapping("/deferResult/{id}")
    public DeferredResult<String> helloDeferredResult(@PathVariable Long id) {
        long startTS = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + " start ");
        DeferredResult<String> result = new DeferredResult<>();
        map.put(id, result);

        return result;
    }


    private void listenMessage(Long id, String resultContent) {
        map.get(id).setResult(resultContent);
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> invalid(RuntimeException exp) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("result", "fail");
        ret.put("errMsg", exp.getMessage());
        return ret;
    }
}
