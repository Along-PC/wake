package com.tourye.wake;

import android.os.Build;

/**
 * Created by longlongren on 2018/8/10.
 * <p>
 * introduce:网络访问地址
 */

public class Constants {
    public static final String BASE_URL= BuildConfig.BASE_URL;
//    public static final String BASE_URL="http://api.wake-test.xorout.com/api/v1";
    public static final String SHARE_URL=BuildConfig.SHARE_URL;

    //获取广告配置
    public static final String OBTAIN_ADVERTISING=BASE_URL+"/framework/promotion_config";
    //获取首页信息
    public static final String HOME_PAGE_INFO=BASE_URL+"/challenge/stats";
    //奖金排行榜
    public static final String RANK_PRIZE=BASE_URL+"/user/rank";
    //运动排行榜----早起棒、坚持榜、膜拜榜
    public static final String RANK_SPORT=BASE_URL+"/sign_in/rank";
    //打卡
    public static final String PUNCH_DATA=BASE_URL+"/sign_in/create";
    //打卡详情数据
    public static final String PUNCH_DETAIL_DATA=BASE_URL+"/sign_in/detail";
    //用户基本信息
    public static final String USER_BASIC_DATA=BASE_URL+"/user/basic_info";
    //用户账户信息
    public static final String USER_ACCOUNT_DATA=BASE_URL+"/user/account";
    //获取成就卡信息
    public static final String ACHIEVEMENT_CARD_DATA=BASE_URL+"/sign_in/achievement_card_sync";
    //复活卡邀请列表
    public static final String RECURRECTION_CARD_INVITE=BASE_URL+"/challenge/revive_invite_record";
    //复活卡卡片记录列表
    public static final String RECURRECTION_CARD_RECORD=BASE_URL+"/challenge/revive_record";
    //复活卡数量
    public static final String RECURRECTION_CARD_NUM=BASE_URL+"/challenge/revive_card_count";
    //邀请卡数据
    public static final String INVITE_CARD_DATA=BASE_URL+"/user/invite_card_sync";
    //局票特权配置
    public static final String PRIVILEGE_CONFIG=BASE_URL+"/privilege/config";
    //壁纸列表
    public static final String WALL_PAPER_LIST=BASE_URL+"/wallpaper";
    //壁纸购买
    public static final String WALL_PAPER_PURCHASE=BASE_URL+"/wallpaper/purchase";
    //账户明细
    public static final String ACCOUNT_DETAIL_DATA=BASE_URL+"/user/account_detail";
    //特权兑换
    public static final String PRIVILEGE_EXCHANGE=BASE_URL+"/privilege/acquire";
    //邀请用户列表
    public static final String INVITED_USER_LIST=BASE_URL+"/challenge_distribution/distribution_list";
    //影响排行榜
    public static final String INFLUENCE_RANK=BASE_URL+"/challenge_distribution/rank";
    //邀请用户详情
    public static final String INVITED_USER_DETAIL=BASE_URL+"/challenge_distribution/stats";
    //修改用户头像或者昵称
    public static final String UPDATE_USER_INFO=BASE_URL+"/user/update_info";
    //上传头像
    public static final String UPLOAD_HEAD=BASE_URL+"/framework/upload_image";
    //打卡记录
    public static final String PUNCH_RECORD=BASE_URL+"/sign_in/records";
    //打卡详情
    public static final String PUNCH_DETAIL=BASE_URL+"/sign_in/detail";
    //账户体现
    public static final String ACCOUNT_WITHDRAW =BASE_URL+"/user/withdraw";
    //获取验证码
    public static final String GET_VERIFY_CODE =BASE_URL+"/auth/verification_code";
    //用户登陆
    public static final String USER_LOGIN =BASE_URL+"/auth/login";
    //根据邀请码记录邀请关系
    public static final String INVITE_CODE_RELATION =BASE_URL+"/user/invite_code";
    //消息列表
    public static final String MESSAGE_LIST =BASE_URL+"/message/list";
    //是否有未读消息
    public static final String UNREAD_MESSAGE_LIST =BASE_URL+"/message/has_unread";
    //读取所有消息
    public static final String READ_MESSAGE =BASE_URL+"/message/read";
    //设置消息接受
    public static final String SET_MESSAGE_RECEIVE =BASE_URL+"/message/receive";
    //局票商城
    public static final String TICKET_SHOP =BASE_URL+"/framework/duiba_url";
    //支付报名
    public static final String PAY_ATTEND =BASE_URL+"/challenge/create_order";
    //支付宝授权信息-----设置支付宝用户id
    public static final String ZFB_AUTH_INFO =BASE_URL+"/user/ali_user_info";
    //激活挑战模式
    public static final String ACTIVE_CHALLENGE =BASE_URL+"/challenge/active";
    //修改设备id
    public static final String UPDATE_DEVICE_ID =BASE_URL+"/user/device";
    //更新用户token
    public static final String UPDATE_TOKEN =BASE_URL+"/user/keep_logged";
    //检查是否需要更新版本
    public static final String CHECK_UPDATE =BASE_URL+"/config/version";


}
