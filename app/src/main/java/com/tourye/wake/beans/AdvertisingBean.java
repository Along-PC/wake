package com.tourye.wake.beans;

import java.io.Serializable;

/**
 * Created by longlongren on 2018/8/10.
 * <p>
 * introduce:获取广告配置实体
 */

public class AdvertisingBean implements Serializable {

    /**
     * status : 0
     * timestamp : 1533892121
     * data : {"home_page_background":{"text":null,"image":"https://ro-test.oss-cn-beijing.aliyuncs.com/promotion_image/20180323034708/0Q3PtH","link":"https://www.baidu.com/"},"home_page_banner":{"text":null,"image":"https://ro-test.oss-cn-beijing.aliyuncs.com/promotion_image/20180323035028/L6fUpr","link":"http://www.pmcaff.com/article/index/1200170974699648?from=search"},"sign_in_share":{"text":null,"image":"https://ro-test.oss-cn-beijing.aliyuncs.com/promotion_image/20180323035433/K3y3OR","link":"http://www.pmcaff.com/article/index/1200170974699648?from=search"},"thumb_up":{"text":null,"image":"https://ro-test.oss-cn-beijing.aliyuncs.com/promotion_image/20180323035449/KIpb20","link":"http://www.pmcaff.com/article/index/1200170974699648?from=search"}}
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
         * home_page_background : {"text":null,"image":"https://ro-test.oss-cn-beijing.aliyuncs.com/promotion_image/20180323034708/0Q3PtH","link":"https://www.baidu.com/"}
         * home_page_banner : {"text":null,"image":"https://ro-test.oss-cn-beijing.aliyuncs.com/promotion_image/20180323035028/L6fUpr","link":"http://www.pmcaff.com/article/index/1200170974699648?from=search"}
         * sign_in_share : {"text":null,"image":"https://ro-test.oss-cn-beijing.aliyuncs.com/promotion_image/20180323035433/K3y3OR","link":"http://www.pmcaff.com/article/index/1200170974699648?from=search"}
         * thumb_up : {"text":null,"image":"https://ro-test.oss-cn-beijing.aliyuncs.com/promotion_image/20180323035449/KIpb20","link":"http://www.pmcaff.com/article/index/1200170974699648?from=search"}
         */

        private HomePageBackgroundBean home_page_background;
        private HomePageBannerBean home_page_banner;
        private SignInShareBean sign_in_share;
        private ThumbUpBean thumb_up;

        public HomePageBackgroundBean getHome_page_background() {
            return home_page_background;
        }

        public void setHome_page_background(HomePageBackgroundBean home_page_background) {
            this.home_page_background = home_page_background;
        }

        public HomePageBannerBean getHome_page_banner() {
            return home_page_banner;
        }

        public void setHome_page_banner(HomePageBannerBean home_page_banner) {
            this.home_page_banner = home_page_banner;
        }

        public SignInShareBean getSign_in_share() {
            return sign_in_share;
        }

        public void setSign_in_share(SignInShareBean sign_in_share) {
            this.sign_in_share = sign_in_share;
        }

        public ThumbUpBean getThumb_up() {
            return thumb_up;
        }

        public void setThumb_up(ThumbUpBean thumb_up) {
            this.thumb_up = thumb_up;
        }

        public static class HomePageBackgroundBean implements Serializable{
            /**
             * text : null
             * image : https://ro-test.oss-cn-beijing.aliyuncs.com/promotion_image/20180323034708/0Q3PtH
             * link : https://www.baidu.com/
             */

            private String text;
            private String image;
            private String link;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }

        public static class HomePageBannerBean implements Serializable{
            /**
             * text : null
             * image : https://ro-test.oss-cn-beijing.aliyuncs.com/promotion_image/20180323035028/L6fUpr
             * link : http://www.pmcaff.com/article/index/1200170974699648?from=search
             */

            private String text;
            private String image;
            private String link;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }

        public static class SignInShareBean implements Serializable{
            /**
             * text : null
             * image : https://ro-test.oss-cn-beijing.aliyuncs.com/promotion_image/20180323035433/K3y3OR
             * link : http://www.pmcaff.com/article/index/1200170974699648?from=search
             */

            private String text;
            private String image;
            private String link;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }

        public static class ThumbUpBean implements Serializable{
            /**
             * text : null
             * image : https://ro-test.oss-cn-beijing.aliyuncs.com/promotion_image/20180323035449/KIpb20
             * link : http://www.pmcaff.com/article/index/1200170974699648?from=search
             */

            private String text;
            private String image;
            private String link;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }
    }
}
