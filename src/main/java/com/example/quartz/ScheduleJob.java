package com.example.quartz;

/**
 * @author 99385
 */
public interface ScheduleJob {
    void execute(String name);
    /**
     * 业务逻辑
     */
    void run();

    /**
     * 更新表达式
     * @param name
     * @return
     */
    boolean updateCronExpression(String name);
}
