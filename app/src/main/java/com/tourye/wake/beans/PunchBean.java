package com.tourye.wake.beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by longlongren on 2018/8/14.
 * <p>
 * introduce:打卡信息实体
 *
 */

public class PunchBean implements Serializable {

    /**
     * status : 0
     * timestamp : 1534213406
     * data : {"waiting":false,"id":1,"time":"xx:xx:xx","defeat":23,"continue":2}
     */

    private int status;
    private int timestamp;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * waiting : false
         * id : 1
         * time : xx:xx:xx
         * defeat : 23
         * continue : 2
         */

        private boolean waiting;
        private int id;
        private String time;
        private int defeat;
        @SerializedName("continue")
        private int continueX;

        public boolean isWaiting() {
            return waiting;
        }

        public void setWaiting(boolean waiting) {
            this.waiting = waiting;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getDefeat() {
            return defeat;
        }

        public void setDefeat(int defeat) {
            this.defeat = defeat;
        }

        public int getContinueX() {
            return continueX;
        }

        public void setContinueX(int continueX) {
            this.continueX = continueX;
        }
    }
}
