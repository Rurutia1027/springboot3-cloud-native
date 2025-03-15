package com.cloud.bookshop.data.batch;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BookFixRateScheduleTask {
    // Fixed rate of 10 seconds
    @Scheduled(fixedRate = 10000)
    public void runEveryTenSeconds() {
        System.out.println("This task runs every 10 seconds");
    }
}
