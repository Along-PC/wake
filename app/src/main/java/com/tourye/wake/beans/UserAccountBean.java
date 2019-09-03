package com.tourye.wake.beans;

import java.io.Serializable;

/**
 * Created by longlongren on 2018/8/15.
 * <p>
 * introduce:用户账户信息实体
 */

public class UserAccountBean implements Serializable {

    /**
     * status : 0
     * timestamp : 1534316961
     * data : {"point":243,"prize":99800,"reward":100300}
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
         * point : 243
         * prize : 99800
         * reward : 100300
         */

        private int point;
        private int prize;
        private int reward;

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public int getPrize() {
            return prize;
        }

        public void setPrize(int prize) {
            this.prize = prize;
        }

        public int getReward() {
            return reward;
        }

        public void setReward(int reward) {
            this.reward = reward;
        }
    }
}
