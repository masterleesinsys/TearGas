package com.fly.teargas.entity;

import java.io.Serializable;

/**
 * 会员信息
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -2385146642281204884L;
    /**
     * member : {"level":1,"grade":1,"vip_expire_date":"","level_text":"VIP1","total_fee":0,"icon":"","grade_text":"一年级"}
     * teacher_collected_count : 0
     * name : 伯恩
     * video_collected_count : 0
     * gender : 1
     * balance : 0
     * type : 2
     * promoting_amount : 0
     * user : 21
     */
    private int teacher_collected_count;
    private String name;
    private int video_collected_count;
    private int gender;
    private int balance;
    private int type;
    private int promoting_amount;
    private int user;
    private String token;     //token
    private int id;     //编号

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getTeacher_collected_count() {
        return teacher_collected_count;
    }

    public void setTeacher_collected_count(int teacher_collected_count) {
        this.teacher_collected_count = teacher_collected_count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVideo_collected_count() {
        return video_collected_count;
    }

    public void setVideo_collected_count(int video_collected_count) {
        this.video_collected_count = video_collected_count;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPromoting_amount() {
        return promoting_amount;
    }

    public void setPromoting_amount(int promoting_amount) {
        this.promoting_amount = promoting_amount;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
}
