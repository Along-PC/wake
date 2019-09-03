package com.tourye.wake.beans;

/**
 * Created by longlongren on 2018/8/29.
 * <p>
 * introduce:阅读所有消息实体
 */

public class ReadMessageBean {

    /**
     * status : 0
     * timestamp : 1535519209
     * data : 0
     */

    private int status;
    private int timestamp;
    private int data;

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

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
