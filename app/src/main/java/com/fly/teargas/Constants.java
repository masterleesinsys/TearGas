package com.fly.teargas;

/**
 * TODO
 * 参数
 */
public class Constants {
    //用户接口
    public static String USER_AUTH = "api/auth/";  //登录
    public static String AUTH_WEIXIN = "api/auth/weixin/";  //微信登录
    public static String USER_TOKEN = "api/token/%s/%d/"; //令牌验证
    public static String GET_VODE = "api/mobile_vcode/"; //请求验证码
    public static String MOBLIE_VODE_CHECK = "api/register/check/"; //手机号和验证码验证.

    public static String USER_REGISTER_MEMBER = "api/register/";   //会员注册
    public static String USER_REGISTER_FINISH = "api/register/finish/";   //会员注册补充资料
    public static String RESET_PASSWORD_CHECK = "api/reset_password/check/";   //忘记密码手机验证
    public static String USER_PASSWORD_FORGET = "api/reset_password/";   //忘记密码
    public static String USER_PASSWORD_CHANGE = "api/user/change_password/";   //修改密码

    //系统接口
    public static String SYSTEM_VISIT = "api/visit/";   //访问统计
    public static String FEEDBACK_SUBMIT = "api/feedback/submit/";   //提交反馈
    public static String FEEDBACK_MINE = "api/feedback/mine/";   //用户的反馈
    public static String VERSION_APP = "api/version/app/";   //app版本版本判断

    //会员接口
    public static String MEMBER_PROFILE = "api/member/profile/";    // 会员信息
    public static String MEMBER_PROFILE_SAVE = "api/member/profile/save/";    //  修改会员信息

    public static String MEMBER_ADDRESS_LIST = "api/member/address/list/";    //  收货地址列表
    public static String MEMBER_ADDRESS_SAVE = "api/member/address/save/";    //  收货地址添加&编辑
    public static String MEMBER_ADDRESS_DEL = "api/member/address/del/";    //  收货地址添加&删除

    public static String MEMBER_COLLECTION_LIST = "api/member/collection/list/";    //  我的收藏,我的关注
    public static String MEMBER_VIDEO_LIST = "api/member/video/list/";    //  我的视频
    public static String MEMBER_TIP_LIST = "api/member/tip/list/";    //  我的打赏
    public static String MEMBER_FOOTMARK_LIST = "api/member/footmark/list/";    //  我的足迹
    public static String MEMBER_PROMOTING_LIST = "api/member/promoting/list/";    //  我的收益金列表

    public static String MEMBER_COLLECTION_ADD = "api/member/collection/add/";    //  添加收藏、关注
    public static String MEMBER_COLLECTION_DEL = "api/member/collection/del/";    //  取消收藏、关注

    //新闻接口
    public static String NEWS_LIST = "api/news/list/";    // 新闻列表
    public static String NEWS_DETAIL_BY_ID = "api/news/%d/";    // 新闻详细信息
    public static String NEWS_DETAIL_BY_TITLE = "api/news/single/";    // 新闻详细信息
    public static String NEWS_READ_STATE = "newsreads_" + MyApplication.getUserId();    // 新闻阅读状态本地缓存文件名

    //广告接口
    public static String ADVERT_LIST = "api/advert/list/";    //广告列表
    public static String ADVERT_VIDEO = "api/advert/video/";    //视频片头广告

    //basic         grade年级  subject科目  press人教版&苏教版    chapter读诗
    public static String BASIC_MODEL_LIST = "api/basic/%s/list/";    //基础资料列表

    //视频相关
    public static String VIDEO_LIST = "api/video/%s/list/";    //课程 同步sync\回放replay\精品featured 列表
    public static String VIDEO = "api/video/%s/";    //视频详情
    public static String VIDEO_ASK_LIST = "api/video/ask/list/";    //互动答疑列表
    public static String VIDEO_TEACH_LIST = "api/video/teach/list/";    //名师培优列表
    public static String VIDEO_PREVIEW_LIST = "api/video/preview/list/";    //课程体验列表
    public static String VIDEO_SEARCH = "api/video/search/";    //搜索
    public static String VIDEO_HOME = "api/video/home/";    //首页视频列表

    //老师相关
    public static String TEACHER_LIST = "api/teacher/list/";    //名师导航
    public static String TEACHER_DETAIL = "api/teacher/%s/detail/";    //教师详情
    public static String TEACHER_COMMENT_ADD = "api/teacher/%d/comment/add/";    //添加教师评论
    public static String TEACHER_COMMENT_LIST = "api/teacher/%d/comment/list/";    //教师评论列表

    //商城相关
    public static String STORE_PRODUCT_CATEGORY_LIST = "api/store/product/category/list/";    //商品分类
    public static String STORE_PRODUCT_LIST = "api/store/product/list/";    //商品列表
    public static String STORE_PRODUCT = "api/store/product/%s/";    //商品详情

    public static String STORE_CART = "api/store/cart/";    //购物车内容
    public static String STORE_CART_ADD = "api/store/cart/add/";    //添加到购物车
    public static String STORE_CART_REMOVE = "api/store/remove/";    //从购物车移除
    public static String STORE_CART_CLEAR = "api/store/cart/clear/";    //清空购物车
    public static String STORE_CART_UPDATE = "api/store/cart/update/";    //更新数量

    //订单相关
    public static String ORDER_PRICE = "api/order/%s/price/";    //读取订单单价

    public static String ORDER_TEACH_SAVE = "api/order/teach/save/";    //名师培优订单保存
    public static String ORDER_ASK_SAVE = "api/order/ask/save/";    //互动答疑优订单保存
    public static String ORDER_REPLAY_SAVE = "api/order/replay/save/";    //课程回放订单保存
    public static String ORDER_TIP_SAVE = "api/order/tip/save/";    //打赏订单保存
    public static String ORDER_VIP_SAVE = "api/order/vip/save/";    //VIP订单保存
    public static String ORDER_STORE_SAVE = "api/order/store/save/";    //商城订单保存

    public static String ORDER_LIST = "api/order/list/";    //我的订单
    public static String ORDER = "api/order/%s/";    //订单详情

    //充值选项相关
    public static String FINANCE_RECHARGER_CONFIG_LIST = "api/finance/recharger_config_list/";

    //支付相关
    public static String PAY_CREATE_ALIPAY = "api/pay/create/alipay/";  //支付宝--创建订单
    public static String PAY_CREATE_BALANCE_PAY = "api/pay/create/balance_pay/";  //余额--创建订单
    public static String PAY_CREATE_WXPAY = "api/pay/create/wxpay/";  //微信支付

    //财务相关
    public static String FINANCE_PROMOTING_LIST = "api/finance/promoting/list/";    // 推广收益

    public static String FINANCE_DRAWING_ACCOUNT_SAVE = "api/finance/drawing/account/save/";    // 保存提现账户
    public static String FINANCE_DRAWING_ACCOUNT = "api/finance/drawing/account/";    // 提现账户

    public static String FINANCE_DRAWING_ADD = "api/finance/drawing/add/";    // 提现
    public static String FINANCE_DRAWING_LIST = "api/finance/drawing/list/";    // 提现记录
}

