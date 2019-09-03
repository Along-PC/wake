package com.tourye.wake.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by longlongren on 2018/8/29.
 * <p>
 * introduce:打卡详情实体
 */

public class PunchDetailBean {

    /**
     * status : 0
     * timestamp : 1535509194
     * data : {"user_id":611,"nickname":"long","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/avatar/2018-08-28//bc7b60a8dda04b34a4c1c3564c1ce98c","time":"09:38:07","defeat":-54,"continue":1,"has_thumb_up":false}
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
         * user_id : 611
         * nickname : long
         * avatar : https://ro-test.oss-cn-beijing.aliyuncs.com/avatar/2018-08-28//bc7b60a8dda04b34a4c1c3564c1ce98c
         * time : 09:38:07
         * defeat : -54
         * continue : 1
         * has_thumb_up : false
         */

        private int user_id;
        private String nickname;
        private String avatar;
        private String time;
        private int defeat;
        @SerializedName("continue")
        private int continueX;
        private boolean has_thumb_up;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
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

        public boolean isHas_thumb_up() {
            return has_thumb_up;
        }

        public void setHas_thumb_up(boolean has_thumb_up) {
            this.has_thumb_up = has_thumb_up;
        }
    }
}
