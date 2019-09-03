package com.tourye.wake.beans;

import java.io.Serializable;

/**
 * Created by longlongren on 2018/8/14.
 * <p>
 * introduce:用户基本信息实体
 */

public class UserBasicBean implements Serializable{


    /**
     * status : 0
     * timestamp : 1534214733
     * data : {"id":2,"serial_number":"15243V","nickname":"？？？？？？？？","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/avatar/2018-08-13//7017e952fa254424b69062b545abc4f5","total_days":13,"continue_days":7,"max_continue_days":7,"subscribe":1,"sign_in_alert_time":"16:10"}
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
         * id : 2
         * serial_number : 15243V
         * nickname : ？？？？？？？？
         * avatar : https://ro-test.oss-cn-beijing.aliyuncs.com/avatar/2018-08-13//7017e952fa254424b69062b545abc4f5
         * total_days : 13
         * continue_days : 7
         * max_continue_days : 7
         * subscribe : 1
         * sign_in_alert_time : 16:10
         */

        private int id;
        private String serial_number;
        private String nickname;
        private String avatar;
        private int total_days;
        private int continue_days;
        private int max_continue_days;
        private int subscribe;
        private String sign_in_alert_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSerial_number() {
            return serial_number;
        }

        public void setSerial_number(String serial_number) {
            this.serial_number = serial_number;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getTotal_days() {
            return total_days;
        }

        public void setTotal_days(int total_days) {
            this.total_days = total_days;
        }

        public int getContinue_days() {
            return continue_days;
        }

        public void setContinue_days(int continue_days) {
            this.continue_days = continue_days;
        }

        public int getMax_continue_days() {
            return max_continue_days;
        }

        public void setMax_continue_days(int max_continue_days) {
            this.max_continue_days = max_continue_days;
        }

        public int getSubscribe() {
            return subscribe;
        }

        public void setSubscribe(int subscribe) {
            this.subscribe = subscribe;
        }

        public String getSign_in_alert_time() {
            return sign_in_alert_time;
        }

        public void setSign_in_alert_time(String sign_in_alert_time) {
            this.sign_in_alert_time = sign_in_alert_time;
        }
    }
}
