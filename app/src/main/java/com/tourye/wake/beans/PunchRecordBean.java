package com.tourye.wake.beans;

import java.util.List;

/**
 * Created by longlongren on 2018/8/23.
 * <p>
 * introduce:打卡记录实体
 */

public class PunchRecordBean {

    /**
     * status : 0
     * timestamp : 1534990574
     * data : [{"date":"2018-08-23","time":"05:00:00","profit":null},{"date":"2018-08-22","time":"05:00:00","profit":null},{"date":"2018-08-21","time":"05:00:00","profit":null},{"date":"2018-08-20","time":"09:56:54","profit":null},{"date":"2018-08-17","time":"10:07:06","profit":null},{"date":"2018-08-16","time":"19:19:03","profit":null},{"date":"2018-08-15","time":"10:29:44","profit":null},{"date":"2018-08-14","time":"10:23:26","profit":null},{"date":"2018-08-13","time":"11:46:29","profit":null},{"date":"2018-08-12","time":"10:27:13","profit":null}]
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
         * date : 2018-08-23
         * time : 05:00:00
         * profit : null
         */

        private String date;
        private String time;
        private String profit;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getProfit() {
            return profit;
        }

        public void setProfit(String profit) {
            this.profit = profit;
        }
    }
}
