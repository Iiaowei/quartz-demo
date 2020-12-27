package com.example.quartz;

abstract class DefaultScheduleJob implements ScheduleJob {

    @Override
    public void execute(String name) {
        System.out.printf("开始执行%s", name);
        run();
        updateCronExpression(name);
    }

    @Override
    public boolean updateCronExpression(String name) {
        return false;
    }
}
