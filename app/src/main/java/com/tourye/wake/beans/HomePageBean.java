package com.tourye.wake.beans;

import java.io.Serializable;

/**
 * Created by longlongren on 2018/8/13.
 * <p>
 * introduce:首页信息实体
 */

public class HomePageBean implements Serializable {

    /**
     * status : 0
     * timestamp : 1534129965
     * data : {"prize_pool":0,"joined":false,"sleep":false,"signed":false,"sign_in_id":null,"is_today":true,"max_prize":0,"pass_people":0,"fail_people":0}
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
         * prize_pool : 0
         * joined : false
         * sleep : false
         * signed : false
         * sign_in_id : null
         * is_today : true
         * max_prize : 0
         * pass_people : 0
         * fail_people : 0
         */

        private int prize_pool;
        private boolean joined;
        private boolean sleep;
        private boolean signed;
        private String sign_in_id;
        private boolean is_today;
        private int max_prize;
        private int pass_people;
        private int fail_people;

        public int getPrize_pool() {
            return prize_pool;
        }

        public void setPrize_pool(int prize_pool) {
            this.prize_pool = prize_pool;
        }

        public boolean isJoined() {
            return joined;
        }

        public void setJoined(boolean joined) {
            this.joined = joined;
        }

        public boolean isSleep() {
            return sleep;
        }

        public void setSleep(boolean sleep) {
            this.sleep = sleep;
        }

        public boolean isSigned() {
            return signed;
        }

        public void setSigned(boolean signed) {
            this.signed = signed;
        }

        public String getSign_in_id() {
            return sign_in_id;
        }

        public void setSign_in_id(String sign_in_id) {
            this.sign_in_id = sign_in_id;
        }

        public boolean isIs_today() {
            return is_today;
        }

        public void setIs_today(boolean is_today) {
            this.is_today = is_today;
        }

        public int getMax_prize() {
            return max_prize;
        }

        public void setMax_prize(int max_prize) {
            this.max_prize = max_prize;
        }

        public int getPass_people() {
            return pass_people;
        }

        public void setPass_people(int pass_people) {
            this.pass_people = pass_people;
        }

        public int getFail_people() {
            return fail_people;
        }

        public void setFail_people(int fail_people) {
            this.fail_people = fail_people;
        }
    }
}
