package com.tourye.wake.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by longlongren on 2018/9/30.
 * <p>
 * introduce:检查是否需要更新实体
 */

public class CheckUpdateBean {

    /**
     * status : 0
     * timestamp : 1538289372
     * message : ok
     * data : {"baseline":1,"newest":1,"package":"https://ro-test.oss-cn-beijing.aliyuncs.com/android_package/1.zip"}
     */

    private int status;
    private int timestamp;
    private String message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * baseline : 1
         * newest : 1
         * package : https://ro-test.oss-cn-beijing.aliyuncs.com/android_package/1.zip
         */

        private int baseline;
        private int newest;
        @SerializedName("package")
        private String packageX;

        public int getBaseline() {
            return baseline;
        }

        public void setBaseline(int baseline) {
            this.baseline = baseline;
        }

        public int getNewest() {
            return newest;
        }

        public void setNewest(int newest) {
            this.newest = newest;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }
    }
}
