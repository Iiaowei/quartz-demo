package com.example.quartz1;

import com.example.quartz.JobViewModel;

import java.util.List;

/**
 * @author 99385
 */
public interface IScheduleServer {
    List<JobViewModel> selectJobByGroupName(String groupName);
}
