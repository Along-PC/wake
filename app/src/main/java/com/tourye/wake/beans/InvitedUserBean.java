package com.tourye.wake.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by longlongren on 2018/8/20.
 * <p>
 * introduce:
 */

public class InvitedUserBean implements Serializable{

    /**
     * status : 0
     * timestamp : 1534750174
     * data : [{"nickname":"于湛","avatar":"http://thirdwx.qlogo.cn/mmopen/ajNVdqHZLLBDoiaNxWc5GhmbIWOfR94519apmoOyCWA5wFEN3aneQicE6lcKjH1JJCiclsAOFLOURQhcc8rl0pBhQ/132"},{"nickname":"于湛","avatar":"http://thirdwx.qlogo.cn/mmopen/ajNVdqHZLLBDoiaNxWc5GhmbIWOfR94519apmoOyCWA5wFEN3aneQicE6lcKjH1JJCiclsAOFLOURQhcc8rl0pBhQ/132"},{"nickname":"于湛","avatar":"http://thirdwx.qlogo.cn/mmopen/ajNVdqHZLLBDoiaNxWc5GhmbIWOfR94519apmoOyCWA5wFEN3aneQicE6lcKjH1JJCiclsAOFLOURQhcc8rl0pBhQ/132"},{"nickname":"于湛","avatar":"http://thirdwx.qlogo.cn/mmopen/ajNVdqHZLLBDoiaNxWc5GhmbIWOfR94519apmoOyCWA5wFEN3aneQicE6lcKjH1JJCiclsAOFLOURQhcc8rl0pBhQ/132"},{"nickname":"于湛","avatar":"http://thirdwx.qlogo.cn/mmopen/ajNVdqHZLLBDoiaNxWc5GhmbIWOfR94519apmoOyCWA5wFEN3aneQicE6lcKjH1JJCiclsAOFLOURQhcc8rl0pBhQ/132"},{"nickname":"于湛","avatar":"http://thirdwx.qlogo.cn/mmopen/ajNVdqHZLLBDoiaNxWc5GhmbIWOfR94519apmoOyCWA5wFEN3aneQicE6lcKjH1JJCiclsAOFLOURQhcc8rl0pBhQ/132"},{"nickname":"于湛","avatar":"http://thirdwx.qlogo.cn/mmopen/ajNVdqHZLLBDoiaNxWc5GhmbIWOfR94519apmoOyCWA5wFEN3aneQicE6lcKjH1JJCiclsAOFLOURQhcc8rl0pBhQ/132"},{"nickname":"于湛","avatar":"http://thirdwx.qlogo.cn/mmopen/ajNVdqHZLLBDoiaNxWc5GhmbIWOfR94519apmoOyCWA5wFEN3aneQicE6lcKjH1JJCiclsAOFLOURQhcc8rl0pBhQ/132"},{"nickname":"于湛","avatar":"http://thirdwx.qlogo.cn/mmopen/ajNVdqHZLLBDoiaNxWc5GhmbIWOfR94519apmoOyCWA5wFEN3aneQicE6lcKjH1JJCiclsAOFLOURQhcc8rl0pBhQ/132"},{"nickname":"于湛","avatar":"http://thirdwx.qlogo.cn/mmopen/ajNVdqHZLLBDoiaNxWc5GhmbIWOfR94519apmoOyCWA5wFEN3aneQicE6lcKjH1JJCiclsAOFLOURQhcc8rl0pBhQ/132"}]
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
         * nickname : 于湛
         * avatar : http://thirdwx.qlogo.cn/mmopen/ajNVdqHZLLBDoiaNxWc5GhmbIWOfR94519apmoOyCWA5wFEN3aneQicE6lcKjH1JJCiclsAOFLOURQhcc8rl0pBhQ/132
         */

        private String nickname;
        private String avatar;

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
    }
}
