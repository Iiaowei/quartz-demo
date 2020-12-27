package com.example.quartz1;

import com.example.quartz.JobViewModel;
import com.example.quartz.ScheduleJob;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liaowei
 */
@Component
public class ScheduleManager implements InitializingBean {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    private Map<String, ScheduleJob> scheduleJobMap = new ConcurrentHashMap<>();
    @Autowired
    private IScheduleServer iScheduleServer;

    public void register(List<JobViewModel> jobViewModels) throws Exception {
        if (jobViewModels.isEmpty()) {
            //return;
        }
        JobViewModel[] jobViewModelArrays = jobViewModels.toArray(new JobViewModel[jobViewModels.size()]);
        Trigger[] triggers = new Trigger[jobViewModelArrays.length];

        for (int i = 0; i < jobViewModelArrays.length; i++) {
            MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
            String jobName = jobViewModelArrays[i].getName();
            jobDetailFactoryBean.setTargetObject(scheduleJobMap.get(jobName));
            jobDetailFactoryBean.setTargetMethod("execute");
            jobDetailFactoryBean.setArguments(jobName);
            jobDetailFactoryBean.setConcurrent(false);
            jobDetailFactoryBean.afterPropertiesSet();

//            jobDetailFactoryBean.setGroup("vvv");
            jobDetailFactoryBean.setName(jobViewModelArrays[i].getName());

            JobDetail jobDetail = jobDetailFactoryBean.getObject();

            CronTriggerFactoryBean triggerFactoryBean = new CronTriggerFactoryBean();
//            triggerFactoryBean.setGroup("b");
//            triggerFactoryBean.setName("aaa");
            triggerFactoryBean.setJobDetail(jobDetail);
//            triggerFactoryBean.setStartDelay(1);
//            triggerFactoryBean.setStartTime(new Date());
            triggerFactoryBean.setCronExpression("0/1 * * * * ?");
            triggerFactoryBean.afterPropertiesSet();
            triggers[i] = triggerFactoryBean.getObject();
        }

        schedulerFactoryBean.setTriggers(triggers);
        schedulerFactoryBean.afterPropertiesSet();
    }

    /**
     * @deprecated
     */
    public void start() {
        if (!schedulerFactoryBean.isRunning()) {
            schedulerFactoryBean.start();
        }
    }

    public boolean add(String jobName) throws NoSuchMethodException, ClassNotFoundException, ParseException, SchedulerException {
        MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        jobDetailFactoryBean.setTargetObject(scheduleJobMap.get(jobName));
        jobDetailFactoryBean.setTargetMethod("execute");
        jobDetailFactoryBean.setArguments(jobName);
        jobDetailFactoryBean.setConcurrent(false);
        jobDetailFactoryBean.afterPropertiesSet();

//            jobDetailFactoryBean.setGroup("vvv");
        jobDetailFactoryBean.setName(jobName);

        JobDetail jobDetail = jobDetailFactoryBean.getObject();

        CronTriggerFactoryBean triggerFactoryBean = new CronTriggerFactoryBean();
//            triggerFactoryBean.setGroup("b");
//            triggerFactoryBean.setName("aaa");
        triggerFactoryBean.setJobDetail(jobDetail);
//            triggerFactoryBean.setStartDelay(1);
//            triggerFactoryBean.setStartTime(new Date());
        triggerFactoryBean.setCronExpression("0/1 * * * * ?");
        triggerFactoryBean.afterPropertiesSet();
        // 把作业和触发器注册到任务调度中
        getScheduler().scheduleJob(triggerFactoryBean.getObject());
        // 启动
        if (!getScheduler().isShutdown()) {
            getScheduler().start();
        }

        return true;
    }

    public boolean pause(String jobName) {
        JobKey key = JobKey.jobKey(jobName);
        try {
            getScheduler().pauseJob(key);
        } catch (SchedulerException e) {
            return false;
        }
        return true;
    }

    public boolean pauseGroup(String groupName) {
        GroupMatcher<JobKey> tGroupMatcher = GroupMatcher.groupEquals(groupName);
        try {

            getScheduler().pauseJobs(tGroupMatcher);
        } catch (SchedulerException e) {
            return false;
        }
        return true;
    }

    public boolean resumeJob(String jobName) {
        JobKey key = JobKey.jobKey(jobName);
        try {
            getScheduler().resumeJob(key);
        } catch (SchedulerException e) {
            return false;
        }
        return true;
    }

    /**
     * @deprecated
     * @param jobName
     * @return
     */
    public boolean deleteJob(String jobName) {
        JobKey key = JobKey.jobKey(jobName);
        try {
            getScheduler().deleteJob(key);
        } catch (SchedulerException e) {
            return false;
        }
        return true;
    }

    public boolean runJobNow(String jobName) {
        JobKey key = JobKey.jobKey(jobName);
        try {
            schedulerFactoryBean.getScheduler().triggerJob(key);
        } catch (SchedulerException e) {
            return false;
        }
        return true;
    }

    public boolean updateCron(String jobName) {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName);
        try {
            CronTrigger cronTrigger = (CronTrigger) getScheduler().getTrigger(triggerKey);
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("cronExpression表达式");
            cronTrigger = cronTrigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();
            getScheduler().rescheduleJob(triggerKey, cronTrigger);
        } catch (SchedulerException e) {
            return false;
        }
        return true;
    }

    public Scheduler getScheduler() {
        return schedulerFactoryBean.getScheduler();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String ip = InetAddress.getLocalHost().getHostAddress();
        List<JobViewModel> jobList = iScheduleServer.selectJobByGroupName(ip);

        if (null != jobList && jobList.size() > 0) {
            register(jobList);
        }
    }
}
