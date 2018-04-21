package com.fly.teargas.entity;

import java.io.Serializable;

/**
 * 设备信息
 */
public class DeviceInfo implements Serializable {

    /**
     * deviceID : 0
     * userID : 0
     * description :
     * lng : 113.7
     * lat : 34.8
     * curIP : 124.160.193.55:57741
     * curState : 撤防+喇叭响
     * recentTime : 2018-03-29T00:17:09
     * bOnLine : false
     */

    private String deviceID;
    private String userID;
    private String description;
    private double lng;
    private double lat;
    private String curIP;
    private String curState;
    private String recentTime;
    private boolean bOnLine;

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getCurIP() {
        return curIP;
    }

    public void setCurIP(String curIP) {
        this.curIP = curIP;
    }

    public String getCurState() {
        return curState;
    }

    public void setCurState(String curState) {
        this.curState = curState;
    }

    public String getRecentTime() {
        return recentTime;
    }

    public void setRecentTime(String recentTime) {
        this.recentTime = recentTime;
    }

    public boolean isbOnLine() {
        return bOnLine;
    }

    public void setbOnLine(boolean bOnLine) {
        this.bOnLine = bOnLine;
    }
}
