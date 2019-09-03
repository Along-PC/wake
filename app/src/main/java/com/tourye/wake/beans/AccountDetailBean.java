package com.tourye.wake.beans;

import java.util.List;

/**
 * Created by longlongren on 2018/8/16.
 * <p>
 * introduce:账户明细实体
 */

public class AccountDetailBean {

    /**
     * status : 0
     * timestamp : 1534385939
     * data : [{"id":9843,"count":8,"time":"2018-08-15 10:29:46","title":"连续打卡奖励"},{"id":9842,"count":1,"time":"2018-08-15 10:29:46","title":"打卡奖励"},{"id":9787,"count":7,"time":"2018-08-14 10:23:29","title":"连续打卡奖励"},{"id":9786,"count":1,"time":"2018-08-14 10:23:29","title":"打卡奖励"},{"id":9740,"count":6,"time":"2018-08-13 11:46:29","title":"连续打卡奖励"},{"id":9739,"count":1,"time":"2018-08-13 11:46:29","title":"打卡奖励"},{"id":9698,"count":5,"time":"2018-08-12 10:27:14","title":"连续打卡奖励"},{"id":9697,"count":1,"time":"2018-08-12 10:27:14","title":"打卡奖励"},{"id":9656,"count":-20,"time":"2018-08-11 20:44:27","title":"兑换壁纸"},{"id":9655,"count":4,"time":"2018-08-11 12:17:05","title":"连续打卡奖励"}]
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
         * id : 9843
         * count : 8
         * time : 2018-08-15 10:29:46
         * title : 连续打卡奖励
         */

        private int id;
        private int count;
        private String time;
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
