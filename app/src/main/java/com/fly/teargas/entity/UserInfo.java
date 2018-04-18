package com.fly.teargas.entity;

import java.io.Serializable;

/**
 * 会员信息
 */
public class UserInfo implements Serializable {
    /**
     * userID : 0
     * qx : 超级管理员
     * dq : 全国
     * user : admin
     * name : 管理员
     * tel : 114
     * registerTime : 2018-02-04T03:14:27
     */

    private String userID;
    private String qx;
    private String dq;
    private String user;
    private String name;
    private String tel;
    private String registerTime;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getQx() {
        return qx;
    }

    public void setQx(String qx) {
        this.qx = qx;
    }

    public String getDq() {
        return dq;
    }

    public void setDq(String dq) {
        this.dq = dq;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }
}
