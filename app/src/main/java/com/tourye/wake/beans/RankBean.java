package com.tourye.wake.beans;

import java.io.Serializable;

/**
 * Created by longlongren on 2018/8/13.
 * <p>
 * introduce:
 */

public class RankBean implements Serializable{
    /**
     * nickname : 要继续可爱呀
     * avatar : https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/3a0cff30af934aa1bdd0e49af71351d2/XMdl
     * score : 183
     */

    private String nickname;
    private String avatar;
    private String score;
    private String rank;

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
