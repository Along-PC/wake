package com.tourye.wake.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by longlongren on 2018/8/15.
 * <p>
 * introduce:局票特权实体
 */

public class PrivilegeConfigBean implements Serializable{

    /**
     * status : 0
     * timestamp : 1534317384
     * data : [{"name":"明日奖金翻倍","desc":"一口气兑换n个，则连续n天翻倍","thumbnail":"https://static.wake.runorout.cn/meta/privilege/cover_prize_double.jpg","cover":"https://static.wake.runorout.cn/meta/privilege/img_prize_double.png","cover_disable":"https://static.wake.runorout.cn/meta/privilege/img_prize_double_disable.png","price":300,"type":"prize_double","count":0},{"name":"明日5点打卡","desc":"一口气兑换n个，则连续n天打卡","thumbnail":"https://static.wake.runorout.cn/meta/privilege/cover_auto_sign_in.jpg","cover":"https://static.wake.runorout.cn/meta/privilege/img_auto_sign_in.png","cover_disable":"https://static.wake.runorout.cn/meta/privilege/img_auto_sign_in_disable.png","price":300,"type":"auto_sign_in","count":0}]
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

    public static class DataBean implements Serializable{
        /**
         * name : 明日奖金翻倍
         * desc : 一口气兑换n个，则连续n天翻倍
         * thumbnail : https://static.wake.runorout.cn/meta/privilege/cover_prize_double.jpg
         * cover : https://static.wake.runorout.cn/meta/privilege/img_prize_double.png
         * cover_disable : https://static.wake.runorout.cn/meta/privilege/img_prize_double_disable.png
         * price : 300
         * type : prize_double
         * count : 0
         */

        private String name;
        private String desc;
        private String thumbnail;
        private String cover;
        private String cover_disable;
        private int price;
        private String type;
        private int count;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getCover_disable() {
            return cover_disable;
        }

        public void setCover_disable(String cover_disable) {
            this.cover_disable = cover_disable;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
