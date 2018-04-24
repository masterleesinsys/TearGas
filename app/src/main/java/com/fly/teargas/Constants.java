package com.fly.teargas;

/**
 * TODO
 * 参数
 */
public class Constants {
    //获取最新App信息(~/app/apkfilename)
    public static String GET_LATEST_APP = "API/getLatestApp";
    //获取账户token
    public static String GET_TOKEN = "API/getToken";
    //获取账户信息
    public static String GET_USER = "API/getUser";
    //判断是否有新的警情信息
    public static String GET_CHECKNEW = "API/checkNew";
    //获取设备信息
    public static String GET_DEVICE = "API/getDevice";
    //获取指定用户的设备
    public static String GET_DEVICES = "API/getDevices";
    //获取设备历史状态（可用于获取报警信息）
    public static String GET_DEVICE_STATES = "API/getDeviceStates";
    //获取设备通信原始数据
    public static String GET_ORIGINAL_DATA = "API/getOriginalData";
    //修改账户密码
    public static String CHANGE_PASSWORD = "API/changePassword";
    //下发爆弹命令
    public static String CMD_BAO_DAN = "API/cmdBaoDan";
    //下发布防命令
    public static String CMD_BU_FANG = "API/cmdBuFang";
    //下发撤防命令
    public static String CMD_CHE_FANG = "API/cmdCheFang";
    //下发查询命令
    public static String CMD_CHA_XUN = "API/cmdChaXun";
    //获取所有权限列表
    public static String GET_ALL_QUAN_XIAN = "API/getAllQuanXian";
    //获取所有地区列表
    public static String GET_ALL_DI_QU = "API/getAllDiQu";
    //获取所有用户列表
    public static String GET_ALL_USER = "API/getAllUser";
    //获取所有模版列表
    public static String GET_ALL_MODEL = "API/getAllModel";
    //获取指定模版，需指定模版ID
    public static String GET_MODEL = "API/getModel";
    //获取操作记录，须指定账户ID
    public static String GET_RECORD_BY_USERID = "API/getRecordByUserID";
    //获取操作记录，须指定设备ID
    public static String GET_RECORD_BY_DEVICEID = "API/getRecordByDeviceID";
    //修改账户信息，须指定操作账户token
    public static String SET_USER_BY_ID = "API/setUserByID";
    //修改设备信息，须指定操作账户token、设备ID
    public static String SET_DEVICE_BY_ID = "API/setDeviceByID";
    //修改模版信息，须指定操作账户token、模版ID
    public static String SET_MODEL_BY_ID = "API/setMOdelByID";
}

