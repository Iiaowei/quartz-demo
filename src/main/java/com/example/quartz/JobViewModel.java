package com.example.quartz;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 字段名                 允许的值                        允许的特殊字符
 *  秒                    0-59                            , - * /
 *  分                    0-59                            , - * /
 *  小时                  0-23                            , - * /
 *  日                    1-31                            , - * ? / L W C
 *  月                    1-12 or JAN-DEC                 , - * /
 *  周几                  1-7 or SUN-SAT                  , - * ? / L C #
 *  年 (可选字段)          empty, 1970-2099                , - * /
 *
 *
 *
 *  “?”字符：表示不确定的值
 *
 *  “,”字符：指定数个值
 *
 *  “-”字符：指定一个值的范围
 *
 *  “/”字符：指定一个值的增加幅度。n/m表示从n开始，每次增加m
 *
 *  “L”字符：用在日表示一个月中的最后一天，用在周表示该月最后一个星期X
 *
 *  “W”字符：指定离给定日期最近的工作日(周一到周五)
 *
 *  “#”字符：表示该月第几个周X。6#3表示该月第3个周五
 */
public class JobViewModel {
    private Long id;
    private String group;
    private String name;
    private String label;
    private String state;
    private String seconds;
    private String minutes;
    private String hours;
    private String dayOfMonth;
    private String Month;
    private String dayOfWeek;
    private String year;
    private Date lastExecuteTime;
    private Date currentExecuteTime;
    private boolean cornModify;
    private String operatorCode;
    private String operatorName;
    private Date createTime;
    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSeconds() {
        return seconds;
    }

    public void setSeconds(String seconds) {
        this.seconds = seconds;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Date getLastExecuteTime() {
        return lastExecuteTime;
    }

    public void setLastExecuteTime(Date lastExecuteTime) {
        this.lastExecuteTime = lastExecuteTime;
    }

    public Date getCurrentExecuteTime() {
        return currentExecuteTime;
    }

    public void setCurrentExecuteTime(Date currentExecuteTime) {
        this.currentExecuteTime = currentExecuteTime;
    }

    public boolean isCornModify() {
        return cornModify;
    }

    public void setCornModify(boolean cornModify) {
        this.cornModify = cornModify;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
