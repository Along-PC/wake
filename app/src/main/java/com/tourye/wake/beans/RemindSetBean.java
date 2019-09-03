package com.tourye.wake.beans;

/**
 * Created by longlongren on 2018/9/10.
 * <p>
 * introduce:设置提醒实体
 */

public class RemindSetBean {

    /**
     * status : 0
     * timestamp : 1536567164
     * data : {"settle":"1","invite":"1"}
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

    public static class DataBean {
        /**
         * settle : 1
         * invite : 1
         */

        private String settle;//早起结算提醒
        private String invite;//邀请成功提醒

        public String getSettle() {
            return settle;
        }

        public void setSettle(String settle) {
            this.settle = settle;
        }

        public String getInvite() {
            return invite;
        }

        public void setInvite(String invite) {
            this.invite = invite;
        }
    }
}
