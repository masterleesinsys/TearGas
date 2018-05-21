package com.fly.teargas.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 设备状态
 */
public class DeviceStateInfo implements Serializable{
    /**
     * deviceID : 1
     * nBaoDan : 0
     * stateIn : 47
     * stateOut : 31
     * v1 : 0
     * v2 : 0
     * v3 : 0
     * reportTime : 2018-04-28T09:51:00
     * time : 2018-04-28T09:48:46
     * info : 撤防
     * inValues : [1,1,1,1,0,1,0,0]
     * outValues : [1,1,1,1,1,0,0,0]
     */

    private String deviceID;
    private int nBaoDan;
    private int stateIn;
    private int stateOut;
    private int v1;
    private int v2;
    private int v3;
    private String reportTime;
    private String time;
    private String info;
    private List<Integer> inValues;
    private List<Integer> outValues;

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public int getNBaoDan() {
        return nBaoDan;
    }

    public void setNBaoDan(int nBaoDan) {
        this.nBaoDan = nBaoDan;
    }

    public int getStateIn() {
        return stateIn;
    }

    public void setStateIn(int stateIn) {
        this.stateIn = stateIn;
    }

    public int getStateOut() {
        return stateOut;
    }

    public void setStateOut(int stateOut) {
        this.stateOut = stateOut;
    }

    public int getV1() {
        return v1;
    }

    public void setV1(int v1) {
        this.v1 = v1;
    }

    public int getV2() {
        return v2;
    }

    public void setV2(int v2) {
        this.v2 = v2;
    }

    public int getV3() {
        return v3;
    }

    public void setV3(int v3) {
        this.v3 = v3;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Integer> getInValues() {
        return inValues;
    }

    public void setInValues(List<Integer> inValues) {
        this.inValues = inValues;
    }

    public List<Integer> getOutValues() {
        return outValues;
    }

    public void setOutValues(List<Integer> outValues) {
        this.outValues = outValues;
    }
}
