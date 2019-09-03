package com.tourye.wake.beans;

/**
 * Created by longlongren on 2018/8/21.
 * <p>
 * introduce:修改用户头像、姓名实体
 */

public class UpdateUserBean {

    /**
     * status : 0
     * timestamp : 1534834427
     * data : null
     */

    private int status;
    private int timestamp;
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
