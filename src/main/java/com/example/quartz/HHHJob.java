package com.example.quartz;

import org.springframework.stereotype.Component;

@Component
public class HHHJob extends DefaultScheduleJob {
    @Override
    public void run() {
        System.out.println("HHHHHHHH");
    }
}
