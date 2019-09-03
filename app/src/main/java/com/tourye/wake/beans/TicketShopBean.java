package com.tourye.wake.beans;

/**
 * Created by longlongren on 2018/8/29.
 * <p>
 * introduce:局票商城实体
 */

public class TicketShopBean {

    /**
     * status : 0
     * timestamp : 1535538615
     * data : http://www.duiba.com.cn/autoLogin/autologin?uid=2&credits=15172&appKey=RFT6wcBmF4c174GAWSUaqm2nmYj&timestamp=1535538615000&sign=6f805e3a2d77be665c356e44574d099b
     */

    private int status;
    private int timestamp;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
