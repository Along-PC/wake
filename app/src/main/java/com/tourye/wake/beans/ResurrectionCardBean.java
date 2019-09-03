package com.tourye.wake.beans;

/**
 * Created by longlongren on 2018/8/14.
 * <p>
 * introduce:复活卡实体
 */

public class ResurrectionCardBean {

    /**
     * status : 0
     * timestamp : 1534246378
     * data : {"card_count":0,"invite_count":0,"revive_count":0}
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
         * card_count : 0
         * invite_count : 0
         * revive_count : 0
         */

        private int card_count;
        private int invite_count;
        private int revive_count;

        public int getCard_count() {
            return card_count;
        }

        public void setCard_count(int card_count) {
            this.card_count = card_count;
        }

        public int getInvite_count() {
            return invite_count;
        }

        public void setInvite_count(int invite_count) {
            this.invite_count = invite_count;
        }

        public int getRevive_count() {
            return revive_count;
        }

        public void setRevive_count(int revive_count) {
            this.revive_count = revive_count;
        }
    }
}
