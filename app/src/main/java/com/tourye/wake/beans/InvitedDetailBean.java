package com.tourye.wake.beans;

import java.io.Serializable;

/**
 * Created by longlongren on 2018/8/20.
 * <p>
 * introduce:邀请用户详情实体
 */

public class InvitedDetailBean implements Serializable{

    /**
     * status : 0
     * timestamp : 1534758036
     * data : {"level_1_count":0,"level_1_money":0,"total_count":7,"total_money":0}
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
         * level_1_count : 0
         * level_1_money : 0
         * total_count : 7
         * total_money : 0
         */

        private int level_1_count;
        private int level_1_money;
        private int total_count;
        private int total_money;

        public int getLevel_1_count() {
            return level_1_count;
        }

        public void setLevel_1_count(int level_1_count) {
            this.level_1_count = level_1_count;
        }

        public int getLevel_1_money() {
            return level_1_money;
        }

        public void setLevel_1_money(int level_1_money) {
            this.level_1_money = level_1_money;
        }

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }

        public int getTotal_money() {
            return total_money;
        }

        public void setTotal_money(int total_money) {
            this.total_money = total_money;
        }
    }
}
