package com.tourye.wake.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by longlongren on 2018/8/13.
 * <p>
 * introduce:奖金排行榜实体
 */

public class UserRankBean implements Serializable {

    /**
     * status : 0
     * timestamp : 1534159017
     * data : {"rank":[{"nickname":"醉凡尘","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/bf3a7fc34cb64f7686dfe2fb6ea3898b/ATaM","score":4899},{"nickname":"张小六","avatar":"http://thirdwx.qlogo.cn/mmopen/ajNVdqHZLLByDKM2icniaZPicwB6JrdV09qOQtgW7CvDrW7icrWGud7IOGibUzSBWLqItibxOibhssy2MvlhkZTRSGLqg/132","score":4216},{"nickname":"卢森煌","avatar":"http://thirdwx.qlogo.cn/mmopen/poTOtkduOicdMYx5VJ8VDlsv3dwypN1pBCn356TaT2htgMrxdslcB0mVznbQ1R8ibPQiaDu25cz6v62CJngMPa0XicHcf4ibzJHkm/132","score":3621},{"nickname":"只说、怀念","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/63ae9ccc46634583bf5ebdc523db37f7/DArR","score":1926},{"nickname":"大笨牛","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/5c08a8e596984d56a5bfa868eeeb5793/y2lc","score":781},{"nickname":"翱翔的小鸟","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/4cdc2211721f4793aec22723b024eca0/bc7I","score":755},{"nickname":"日月星辰","avatar":"http://thirdwx.qlogo.cn/mmopen/HtXf2eb8Qc0qlibVre1nYVHwyK1FcRmKr4zibGGeIt1Kbsiao1TgRGsReIuZAVbkpj94hCsicl63dOjq7Qb5tBRicZwq1Wosibg9jw/132","score":755},{"nickname":"醒着做梦","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/6b046db5b395433ab937e1a574ffd794/A5Mo","score":748},{"nickname":"韩小七","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/d68cb2f9c8a14b889271807463a1f998/32id","score":717},{"nickname":"英英快乐","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/066b935f7e454a4e99c33dd864c3d94d/DzsU","score":699}],"selfRank":{"nickname":"？？？？？？？？","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/avatar/2018-08-13//7017e952fa254424b69062b545abc4f5","score":0,"rank":"100+"}}
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
         * rank : [{"nickname":"醉凡尘","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/bf3a7fc34cb64f7686dfe2fb6ea3898b/ATaM","score":4899},{"nickname":"张小六","avatar":"http://thirdwx.qlogo.cn/mmopen/ajNVdqHZLLByDKM2icniaZPicwB6JrdV09qOQtgW7CvDrW7icrWGud7IOGibUzSBWLqItibxOibhssy2MvlhkZTRSGLqg/132","score":4216},{"nickname":"卢森煌","avatar":"http://thirdwx.qlogo.cn/mmopen/poTOtkduOicdMYx5VJ8VDlsv3dwypN1pBCn356TaT2htgMrxdslcB0mVznbQ1R8ibPQiaDu25cz6v62CJngMPa0XicHcf4ibzJHkm/132","score":3621},{"nickname":"只说、怀念","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/63ae9ccc46634583bf5ebdc523db37f7/DArR","score":1926},{"nickname":"大笨牛","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/5c08a8e596984d56a5bfa868eeeb5793/y2lc","score":781},{"nickname":"翱翔的小鸟","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/4cdc2211721f4793aec22723b024eca0/bc7I","score":755},{"nickname":"日月星辰","avatar":"http://thirdwx.qlogo.cn/mmopen/HtXf2eb8Qc0qlibVre1nYVHwyK1FcRmKr4zibGGeIt1Kbsiao1TgRGsReIuZAVbkpj94hCsicl63dOjq7Qb5tBRicZwq1Wosibg9jw/132","score":755},{"nickname":"醒着做梦","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/6b046db5b395433ab937e1a574ffd794/A5Mo","score":748},{"nickname":"韩小七","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/d68cb2f9c8a14b889271807463a1f998/32id","score":717},{"nickname":"英英快乐","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/066b935f7e454a4e99c33dd864c3d94d/DzsU","score":699}]
         * selfRank : {"nickname":"？？？？？？？？","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/avatar/2018-08-13//7017e952fa254424b69062b545abc4f5","score":0,"rank":"100+"}
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

    }
}
