package com.tourye.wake.beans;

/**
 * Created by longlongren on 2018/9/3.
 * <p>
 * introduce:阿里授权信息实体
 */

public class AliAuthBean {

    /**
     * status : 0
     * timestamp : 1535959977
     * data : {"uid":2,"parameters":"apiname=com.alipay.account.auth&method=alipay.open.auth.sdk.code.get&app_id=2018082161152087&app_name=mc&biz_type=openservice&pid=2088231395185177&product_id=APP_FAST_LOGIN&scope=kuaijie&target_id=80fce71162d6461caf06caaf547ea094&auth_type=AUTHACCOUNT&sign_type=RSA2&sign=o2%2BlGhQPEbVqPt0SEsswn2lp%2BKJDPRA%2Bx3LeqYqZImji8SXlG5N%2Fi7kBKdKKY4ksIZ6nVp6SkoQVHkQ0lYdpHLlCMWr%2Fdeg1G%2BWNrGVjHnaJMKqTAIF8WcQkTshByXyRsSwRbS8f0Kv%2BpWf15OVVUMUUSro1xKGWcouvUsY2eeyGfb8fLMOjW99wnREc%2FpKcaQ2JVfk0%2BRlJy8%2F4QH0LvtRjkCnzO87c%2FyBsYvF39cblaME%2F8SBTktVyZnW9m%2BoHFVIf3cEgo9uCG%2FzDHrW1zddwWsrN4zLejau7PGF3Vz8GVYZc%2Fg84SJlAO%2FdCA8wYFlKo4ewVEW%2B756rb4WIKAA%3D%3D"}
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
         * uid : 2
         * parameters : apiname=com.alipay.account.auth&method=alipay.open.auth.sdk.code.get&app_id=2018082161152087&app_name=mc&biz_type=openservice&pid=2088231395185177&product_id=APP_FAST_LOGIN&scope=kuaijie&target_id=80fce71162d6461caf06caaf547ea094&auth_type=AUTHACCOUNT&sign_type=RSA2&sign=o2%2BlGhQPEbVqPt0SEsswn2lp%2BKJDPRA%2Bx3LeqYqZImji8SXlG5N%2Fi7kBKdKKY4ksIZ6nVp6SkoQVHkQ0lYdpHLlCMWr%2Fdeg1G%2BWNrGVjHnaJMKqTAIF8WcQkTshByXyRsSwRbS8f0Kv%2BpWf15OVVUMUUSro1xKGWcouvUsY2eeyGfb8fLMOjW99wnREc%2FpKcaQ2JVfk0%2BRlJy8%2F4QH0LvtRjkCnzO87c%2FyBsYvF39cblaME%2F8SBTktVyZnW9m%2BoHFVIf3cEgo9uCG%2FzDHrW1zddwWsrN4zLejau7PGF3Vz8GVYZc%2Fg84SJlAO%2FdCA8wYFlKo4ewVEW%2B756rb4WIKAA%3D%3D
         */

        private int uid;
        private String parameters;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getParameters() {
            return parameters;
        }

        public void setParameters(String parameters) {
            this.parameters = parameters;
        }
    }
}
