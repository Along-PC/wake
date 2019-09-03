package com.tourye.wake.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by longlongren on 2018/8/13.
 * <p>
 * introduce:
 */

public class SportRankBean implements Serializable {

    /**
     * status : 0
     * timestamp : 1534132191
     * data : {"rank":[{"nickname":"要继续可爱呀","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/3a0cff30af934aa1bdd0e49af71351d2/XMdl","score":183},{"nickname":"韩小七","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/d68cb2f9c8a14b889271807463a1f998/32id","score":183},{"nickname":"玫瑰yw","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/c332ab430a804e2eac6715c06435c7a4/mvRU","score":183},{"nickname":"秃秃一","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/260cd1c348ae4ef187099a1c7a9f8a53/SbX1","score":183},{"nickname":"英英快乐","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/066b935f7e454a4e99c33dd864c3d94d/DzsU","score":183},{"nickname":"初学者","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/1585c3aadc06411d9ea8983c38f6874d/aVCk","score":183},{"nickname":"别样别样","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/00da92a177d841f2855f4239da3c1911/uBQI","score":183},{"nickname":"雨切","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/8857542acebb4dd0bb581987d91d0cf5/RiSE","score":183},{"nickname":"向阳奔跑","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/83960c586c4a44abb2f50331399461ca/dUoz","score":183},{"nickname":"大笨牛","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/5c08a8e596984d56a5bfa868eeeb5793/y2lc","score":183}],"selfRank":{"nickname":"？？？？？？？？","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/avatar/2018-08-13//7017e952fa254424b69062b545abc4f5","score":6,"rank":23}}
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
         * rank : [{"nickname":"要继续可爱呀","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/3a0cff30af934aa1bdd0e49af71351d2/XMdl","score":183},{"nickname":"韩小七","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/d68cb2f9c8a14b889271807463a1f998/32id","score":183},{"nickname":"玫瑰yw","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/c332ab430a804e2eac6715c06435c7a4/mvRU","score":183},{"nickname":"秃秃一","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/260cd1c348ae4ef187099a1c7a9f8a53/SbX1","score":183},{"nickname":"英英快乐","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/066b935f7e454a4e99c33dd864c3d94d/DzsU","score":183},{"nickname":"初学者","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/1585c3aadc06411d9ea8983c38f6874d/aVCk","score":183},{"nickname":"别样别样","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/00da92a177d841f2855f4239da3c1911/uBQI","score":183},{"nickname":"雨切","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/8857542acebb4dd0bb581987d91d0cf5/RiSE","score":183},{"nickname":"向阳奔跑","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/83960c586c4a44abb2f50331399461ca/dUoz","score":183},{"nickname":"大笨牛","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/user_avatar/2018-02-07/5c08a8e596984d56a5bfa868eeeb5793/y2lc","score":183}]
         * selfRank : {"nickname":"？？？？？？？？","avatar":"https://ro-test.oss-cn-beijing.aliyuncs.com/avatar/2018-08-13//7017e952fa254424b69062b545abc4f5","score":6,"rank":23}
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
