package com.example.quartz1;

import com.example.quartz.JobViewModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleServerImpl implements IScheduleServer {
    @Override
    public List<JobViewModel> selectJobByGroupName(String groupName) {
        return new ArrayList<>();
    }
}
