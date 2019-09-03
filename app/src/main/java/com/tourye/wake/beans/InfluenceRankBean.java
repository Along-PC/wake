package com.tourye.wake.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by longlongren on 2018/8/20.
 * <p>
 * introduce:
 */

public class InfluenceRankBean implements Serializable{

    /**
     * status : 0
     * timestamp : 1534753540
     * data : {"rank":[{"nickname":"明黄","avatar":"http://thirdwx.qlogo.cn/mmopen/poTOtkduOiceZFD6PAl0SszsbfthZSM4viaIWH5qoqSvyyphmSwkB6QRVPTrAfPKAaOdEiatVQjdFogzSk0gwoyA3Sze7rNrgibib/132","score":966},{"nickname":"Beta老师","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/WR3IWIc7tl4OlZAGj6uJLJa4y1ianAJPZwMDiciaBR0jKH3JdxsIxkQOiaV8Auxq9z20qNNt74P60LuTYhZPB1uricg/132","score":963},{"nickname":"丶晚风℡¹\u2075\u2077\u2076³³\u2077\u2077\u2077³\u2077","avatar":"http://thirdwx.qlogo.cn/mmopen/poTOtkduOicdMYx5VJ8VDljicBkINKj5CeDkQ4VC2yD3GCd2gJT6SazHTqghS1emtDjChkgT1PrdPWCBPb4t9s44Jcd62pP1Bq/132","score":954},{"nickname":"袁晓","avatar":"http://thirdwx.qlogo.cn/mmopen/poTOtkduOicevglmcbbLg4zO5PKIQOE2QSIxzyQeIafvygI0jKMWb1LnVibHCKGPapS433Y91qia1l6M6nBOpJMNicwUm14QDn7Q/132","score":934},{"nickname":"萱儿💭哒橘子🍊","avatar":"http://thirdwx.qlogo.cn/mmopen/Q3auHgzwzM54Jqp8OxMibjOM2Epic0ICopPfoCXwQjcpGLoLsoQeTRaEnCYgHL41kD91f4gkzOIw5qky7K5RdKhT8iaeAp8fOhcWCDS8m7WDaY/132","score":899},{"nickname":"美林家潮童尚品工厂实体店","avatar":"http://wx.qlogo.cn/mmopen/HtXf2eb8Qc23X9hNbW5Y4UNKclQI7FgeaxcaTYKsDUvmFznmWHOcdFo95sDzkdJnz9WqhEgic8E9vKuicroM4EK5z6pnm6VjIA/132","score":892},{"nickname":"州州","avatar":"http://wx.qlogo.cn/mmopen/ajNVdqHZLLCicbYN6rFA3FQPvTz6XwV6LoljHc2MrXRF1V91sI9Zql8fIicpowf19ibkcITDBmFKTJTjVht6hlreQ/132","score":890},{"nickname":"杨酉文","avatar":"http://thirdwx.qlogo.cn/mmopen/poTOtkduOicdMYx5VJ8VDlpAoARzo7tTP4Fib9Tgg77VD3iaruV0VB0NNhS9DKibhTSJMRFL7WuVo0vdukyaA8nNGN81EF5fhFhm/132","score":889},{"nickname":"🌈          🐢","avatar":"http://thirdwx.qlogo.cn/mmopen/BO1qQiajiacVlPGCBmPA779cWmpf1HFTX05bKIHyTphymAaHh3jIVDVHAcBhiaZcZIkhdPxnqkk8kPJDBGqjEQtIz0QQ99rRdDR/132","score":843},{"nickname":"徐婷","avatar":"http://thirdwx.qlogo.cn/mmopen/UjL7NSrg1hTHqUjQGy0XFploAmGicuk7x5eyYZrLpdMsfJtZRf6gModVywbJBgvBIXkxJBCqBybegW2zdBnIZSic8NeUCWWGHj/132","score":824}],"selfRank":{"nickname":"于湛","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-08-16/15243V/D4SB","score":7,"rank":37}}
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
         * rank : [{"nickname":"明黄","avatar":"http://thirdwx.qlogo.cn/mmopen/poTOtkduOiceZFD6PAl0SszsbfthZSM4viaIWH5qoqSvyyphmSwkB6QRVPTrAfPKAaOdEiatVQjdFogzSk0gwoyA3Sze7rNrgibib/132","score":966},{"nickname":"Beta老师","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/WR3IWIc7tl4OlZAGj6uJLJa4y1ianAJPZwMDiciaBR0jKH3JdxsIxkQOiaV8Auxq9z20qNNt74P60LuTYhZPB1uricg/132","score":963},{"nickname":"丶晚风℡¹\u2075\u2077\u2076³³\u2077\u2077\u2077³\u2077","avatar":"http://thirdwx.qlogo.cn/mmopen/poTOtkduOicdMYx5VJ8VDljicBkINKj5CeDkQ4VC2yD3GCd2gJT6SazHTqghS1emtDjChkgT1PrdPWCBPb4t9s44Jcd62pP1Bq/132","score":954},{"nickname":"袁晓","avatar":"http://thirdwx.qlogo.cn/mmopen/poTOtkduOicevglmcbbLg4zO5PKIQOE2QSIxzyQeIafvygI0jKMWb1LnVibHCKGPapS433Y91qia1l6M6nBOpJMNicwUm14QDn7Q/132","score":934},{"nickname":"萱儿💭哒橘子🍊","avatar":"http://thirdwx.qlogo.cn/mmopen/Q3auHgzwzM54Jqp8OxMibjOM2Epic0ICopPfoCXwQjcpGLoLsoQeTRaEnCYgHL41kD91f4gkzOIw5qky7K5RdKhT8iaeAp8fOhcWCDS8m7WDaY/132","score":899},{"nickname":"美林家潮童尚品工厂实体店","avatar":"http://wx.qlogo.cn/mmopen/HtXf2eb8Qc23X9hNbW5Y4UNKclQI7FgeaxcaTYKsDUvmFznmWHOcdFo95sDzkdJnz9WqhEgic8E9vKuicroM4EK5z6pnm6VjIA/132","score":892},{"nickname":"州州","avatar":"http://wx.qlogo.cn/mmopen/ajNVdqHZLLCicbYN6rFA3FQPvTz6XwV6LoljHc2MrXRF1V91sI9Zql8fIicpowf19ibkcITDBmFKTJTjVht6hlreQ/132","score":890},{"nickname":"杨酉文","avatar":"http://thirdwx.qlogo.cn/mmopen/poTOtkduOicdMYx5VJ8VDlpAoARzo7tTP4Fib9Tgg77VD3iaruV0VB0NNhS9DKibhTSJMRFL7WuVo0vdukyaA8nNGN81EF5fhFhm/132","score":889},{"nickname":"🌈          🐢","avatar":"http://thirdwx.qlogo.cn/mmopen/BO1qQiajiacVlPGCBmPA779cWmpf1HFTX05bKIHyTphymAaHh3jIVDVHAcBhiaZcZIkhdPxnqkk8kPJDBGqjEQtIz0QQ99rRdDR/132","score":843},{"nickname":"徐婷","avatar":"http://thirdwx.qlogo.cn/mmopen/UjL7NSrg1hTHqUjQGy0XFploAmGicuk7x5eyYZrLpdMsfJtZRf6gModVywbJBgvBIXkxJBCqBybegW2zdBnIZSic8NeUCWWGHj/132","score":824}]
         * selfRank : {"nickname":"于湛","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-08-16/15243V/D4SB","score":7,"rank":37}
         */

        private RankBean selfRank;
        private List<RankBean> rank;

        public RankBean getSelfRank() {
            return selfRank;
        }

        public void setSelfRank(RankBean selfRank) {
            this.selfRank = selfRank;
        }

        public List<RankBean> getRank() {
            return rank;
        }

        public void setRank(List<RankBean> rank) {
            this.rank = rank;
        }


        public static class RankBean implements Serializable{
            /**
             * nickname : 明黄
             * avatar : http://thirdwx.qlogo.cn/mmopen/poTOtkduOiceZFD6PAl0SszsbfthZSM4viaIWH5qoqSvyyphmSwkB6QRVPTrAfPKAaOdEiatVQjdFogzSk0gwoyA3Sze7rNrgibib/132
             * score : 966
             */

            private String nickname;
            private String avatar;
            private int score;
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

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }
        }
    }
}
