package com.tourye.wake.beans;

/**
 * Created by longlongren on 2018/8/14.
 * <p>
 * introduce:成就卡信息实体
 */

public class AchievementCardBean {

    /**
     * status : 0
     * timestamp : 1534217999
     * data :
     */

    private int status;
    private int timestamp;
    private String data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
