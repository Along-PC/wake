package com.tourye.wake.beans;

import java.io.Serializable;

/**
 * Created by longlongren on 2018/8/29.
 * <p>
 * introduce:是否有未读消息
 */

public class UnreadMessageBean implements Serializable{

    /**
     * status : 0
     * timestamp : 1535514739
     * data : false
     */

    private int status;
    private int timestamp;
    private boolean data;

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

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}
