package com.example.quartz;

import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @date 2020/12/26
 * @author liaowei
 */
//@Configuration
public class QuartzConfig {

    @Bean
    public MethodInvokingJobDetailFactoryBean detailFactoryBean(ScheduleJob job) {
        MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
        bean.setTargetObject(job);
        bean.setTargetMethod("sayHello");
        bean.setConcurrent(false);
        return bean;
    }

    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean(MethodInvokingJobDetailFactoryBean detailFactoryBean) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(detailFactoryBean.getObject());
        trigger.setCronExpression("0/1 * * * * ?");
        return trigger;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactory(Trigger[] cronTriggerFactoryBean) {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setTriggers(cronTriggerFactoryBean);
        return scheduler;
    }

}
