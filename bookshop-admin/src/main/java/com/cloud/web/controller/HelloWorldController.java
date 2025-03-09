package com.cloud.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {

    @GetMapping("/exception")
    public String exception() {
        throw new RuntimeException("exception test endpoint");
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
