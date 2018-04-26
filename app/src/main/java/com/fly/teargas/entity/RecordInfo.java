package com.fly.teargas.entity;

import java.io.Serializable;

/**
 * 设备操作记录
 */
public class RecordInfo implements Serializable {
    /**
     * no : 201
     * userID : 0
     * deviceID : 1
     * text : 查询与校时命令
     * time : 2018-04-03T13:57:09
     * operatorName : 管理员
     */
    private String no;
    private String userID;
    private String deviceID;
    private String text;
    private String time;
    private String operatorName;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
}
