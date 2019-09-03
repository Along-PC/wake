package com.tourye.wake.beans;

import java.util.List;

/**
 * Created by longlongren on 2018/8/15.
 * <p>
 * introduce:壁纸实体
 */

public class WallPaperBean {

    /**
     * status : 0
     * timestamp : 1534319798
     * data : [{"id":40,"url":"https://ro-test.oss-cn-beijing.aliyuncs.com/achievement_card_background/20180129115325/WNE4z3","price":20,"purchased":false},{"id":39,"url":"https://ro-test.oss-cn-beijing.aliyuncs.com/achievement_card_background/20180129115325/WNE4z3","price":20,"purchased":false},{"id":38,"url":"https://ro-test.oss-cn-beijing.aliyuncs.com/achievement_card_background/20180129115325/WNE4z3","price":20,"purchased":false},{"id":37,"url":"https://ro-test.oss-cn-beijing.aliyuncs.com/achievement_card_background/20180129115325/WNE4z3","price":20,"purchased":false},{"id":36,"url":"https://ro-test.oss-cn-beijing.aliyuncs.com/achievement_card_background/20180129115325/WNE4z3","price":20,"purchased":false},{"id":35,"url":"https://ro-test.oss-cn-beijing.aliyuncs.com/achievement_card_background/20180129115325/WNE4z3","price":20,"purchased":false},{"id":34,"url":"https://ro-test.oss-cn-beijing.aliyuncs.com/achievement_card_background/20180129115325/WNE4z3","price":20,"purchased":false},{"id":33,"url":"https://ro-test.oss-cn-beijing.aliyuncs.com/achievement_card_background/20180129115325/WNE4z3","price":20,"purchased":false},{"id":32,"url":"https://ro-test.oss-cn-beijing.aliyuncs.com/achievement_card_background/20180129115325/WNE4z3","price":20,"purchased":false},{"id":31,"url":"https://ro-test.oss-cn-beijing.aliyuncs.com/achievement_card_background/20180129115325/WNE4z3","price":20,"purchased":true}]
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
         * id : 40
         * url : https://ro-test.oss-cn-beijing.aliyuncs.com/achievement_card_background/20180129115325/WNE4z3
         * price : 20
         * purchased : false
         */

        private int id;
        private String url;
        private int price;
        private boolean purchased;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public boolean isPurchased() {
            return purchased;
        }

        public void setPurchased(boolean purchased) {
            this.purchased = purchased;
        }
    }
}
