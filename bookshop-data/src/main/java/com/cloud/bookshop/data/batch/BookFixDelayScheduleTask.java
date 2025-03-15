package com.cloud.bookshop.data.batch;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BookFixDelayScheduleTask {
    // Fixed delay of 5 seconds after the previous task finishes
    @Scheduled(fixedDelay = 5000)
    public void runWithFixedDelay() {
        System.out.println("This task runs with a fixed delay of 5 seconds");
    }
}
