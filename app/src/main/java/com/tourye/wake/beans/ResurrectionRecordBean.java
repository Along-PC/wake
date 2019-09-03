package com.tourye.wake.beans;

import java.util.List;

/**
 * Created by longlongren on 2018/8/14.
 * <p>
 * introduce:复活卡卡片记录列表
 */

public class ResurrectionRecordBean {

    /**
     * status : 0
     * timestamp : 1534238447
     * data : [{"time":"xxxx-xx-xx xx:xx:xx"}]
     */

    private int status;
    private int timestamp;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : xxxx-xx-xx xx:xx:xx
         */

        private String time;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
