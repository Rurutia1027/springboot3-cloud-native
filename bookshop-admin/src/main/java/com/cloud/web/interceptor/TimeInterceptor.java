package com.cloud.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Objects;

@Component
public class TimeInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("startTime", new Date().getTime());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("#postHandle");
        System.out.println("server processing time consume: " + (new Date().getTime() - (Long) request.getAttribute("startTime")) + " ms");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("#afterCompletion");
        if (Objects.nonNull(ex)) {
            System.out.println("Exp " + ex.getMessage());
        }
        System.out.println("server processing time consume: " + (new Date().getTime() - (Long) request.getAttribute("startTime")) + " ms");
    }
}
