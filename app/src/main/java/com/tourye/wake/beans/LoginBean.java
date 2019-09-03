package com.tourye.wake.beans;

/**
 * Created by longlongren on 2018/8/24.
 * <p>
 * introduce:登陆实体
 */

public class LoginBean {

    /**
     * status : 0
     * timestamp : 1535110147
     * data : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NjY2NDYxNDcsImlhdCI6MTUzNTExMDE0NywidWlkIjoiMTUyOFQ3In0.XTijvqBVO-LYxQyPgMPMhSbuPolVw2vNr-3ocPhJD5A
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
