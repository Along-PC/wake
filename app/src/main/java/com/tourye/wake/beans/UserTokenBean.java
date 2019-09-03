package com.tourye.wake.beans;

/**
 * Created by longlongren on 2018/9/7.
 * <p>
 * introduce:用户更新token实体
 */

public class UserTokenBean {

    /**
     * status : 0
     * timestamp : 1536297075
     * data : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MzY3MjkwNzUsImlhdCI6MTUzNjI5NzA3NSwidWlkIjoiMTUyNDNWIn0.9-4Nzk4xBoCwWF8KecM_UDG3b4FcD8FkgPBNA2kLFMM
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
