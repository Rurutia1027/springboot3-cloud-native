package com.cloud.bookshop.data.batch;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BookScheduleTask {
    // Cron expression to run every minute
    @Scheduled(cron = "0 */5 * * * *")
    public void runEveryMinute() {
        System.out.println("This task runs every 5 minutes");
    }
}
