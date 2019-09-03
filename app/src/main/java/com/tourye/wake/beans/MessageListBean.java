package com.tourye.wake.beans;

import java.util.List;

/**
 * Created by longlongren on 2018/8/29.
 * <p>
 * introduce:消息列表实体
 */

public class MessageListBean {


    /**
     * status : 0
     * timestamp : 1535510303
     * data : [{"title":"标题","content":"内容","url":"图片","detail":"","date_time":"2019-01-01 12:22:21"}]
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
         * title : 标题
         * content : 内容
         * url : 图片
         * detail : 是否有下一级页面
         * date_time : 2019-01-01 12:22:21
         */

        private String title;
        private String content;
        private String url;
        private String detail;
        private String date_time;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getDate_time() {
            return date_time;
        }

        public void setDate_time(String date_time) {
            this.date_time = date_time;
        }
    }
}
