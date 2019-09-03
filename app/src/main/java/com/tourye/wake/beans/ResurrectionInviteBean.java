package com.tourye.wake.beans;

import java.util.List;

/**
 * Created by longlongren on 2018/8/14.
 * <p>
 * introduce:复活卡邀请列表实体
 */

public class ResurrectionInviteBean {

    /**
     * status : 0
     * timestamp : 1534236933
     * data : [{"nickname":"xxx","avatar":"xxx","time":"xxxx-xx-xx xx:xx:xx"}]
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
         * nickname : xxx
         * avatar : xxx
         * time : xxxx-xx-xx xx:xx:xx
         */

        private String nickname;
        private String avatar;
        private String time;

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

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
