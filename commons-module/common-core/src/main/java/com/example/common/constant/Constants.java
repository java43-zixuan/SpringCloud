package com.example.common.constant;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhangyu on 2017/3/14.
 */
public class Constants {

    // 坐标 Double 到 Long 的转换
    public static final int COORD_FORMAT = 1000000;

    // 添加标记：0 未添加
    public static final int IS_NOT_ADD = 0;

    // 添加标记：1 已添加
    public static final int HAVE_ADD = 1;

    // 删除区分：0未删除
    public static final int IS_NOT_DEL = 0;

    // 删除区分：1已删除
    public static final int HAVE_DEL = 1;

    // 启用
    public static final int STATE_ENABLE = 1;

    // 禁用
    public static final int STATE_DISABLED = 0;

    //电子围栏：0  手动创建
    public static final int MANUAL_CREATE = 0;

    //电子围栏：1  自动创建
    public static final int AUTO_CREATE = 1;

    //服务站电子围栏
    public static final int STATION_CREATE = 0;

    //修理厂电子围栏
    public static final int REPAIR_CREATE = 1;

    //是否全部展示
    public static final int IS_ALL_SHOW = 2;

    // token key
    public static final String TOKEN_KEY = "STOKENS_";

    //session key
    public static final String SESSION_KEY = "SESSION_";

    // openapi token key
    public static final String OPENAPI_TOKEN_KEY = "OPENAPI_TOKEN_KEY_";

    // 验证码 key
    public static final String VERIFYCODE_KEY = "VERIFYCODE_";

    // 登陆失败key
    public static final String ERROR_LOGIN_KEY = "ERROR_LOGIN_KEY_";

    // _
    public static final String SYMBO_UNDERLINE = "_";

    // *
    public static final String SYMBO_NUMBERSIGN = "*";

    // 校验token开关key
    public static final String VALIDATE_TOKEN = "validate.token";

    // http 超时时间
    public static final String CONNECTION_REQUEST_TIMEOUT = "connection.request.timeout";

    // http 超时时间
    public static final String CONNECT_TIMEOUT = "connect.timeout";

    // http 超时时间
    public static final String READ_TIMEOUT = "read.timeout";

    // token校验接口
    // public static final String VALIDATE_TOKEN_URL = "validate.token.url";

    // 用户校验接口
    // public static final String VALIDATE_USER_URL = "validate.user.url";

    // 指令消息缓存key前缀
    public static final String MSG_KEY_PREFIX = "MSGPUSH_";

    // 注册平台key
    public static final String ADD_PLATINFO_KEY = "ADDPLATINFO_";

    // 油量设置key
    public static final String OIL_SETTING_REPONSE_KEY = "OILSETTINGREPONSE";

    public static final String OTA_SETTING_EXIST_KEY = "OTASETTINGEXIST";

    public static final String DBC_DOWNLOAD_EXIST_KEY = "DBCDOWNLOADEXIST";

    public static final String OTA_ACTIVE_ALLIANCE_KEY = "OTAACTIVEALLIANCE";

    // 下发离线指令
    public static final boolean SEND_OFFLINE_COMMAND = true;
    // 不下发离线指令
    public static final boolean NOT_SEND_OFFLINE_COMMAND = false;
    // 分段指令
    public static final boolean RESUME_INSTRUCTION = true;
    // 非分段指令
    public static final boolean NOT_RESUME_INSTRUCTION = false;
    // 轮胎字典表DATA_TYPE
    public static final int TIRE_LOCATION_TYPE = 100;

    public static final String RESULT_NULL = "返回结果为：null";

    public static final String PUSH_MESSAGE_TOPIC = "push_message_t";

    /**
     * push模块kafkaCommandID
     */
    public static final String PUSH_KAFKA_COMMANDID = "0001";


    // 添加服务站不校验唯一性
    public static final String NO_CHECK_UNIQUE = "-1";
    public static final String TERMINAL_FIRST_ONLINE_TIME_KEY = "TERMINAL_FIRST_ONLINE_TIME_KEY";

    public static final int ENTRY_METHOD_ADD = 0;
    public static final int ENTRY_METHOD_SYNCHRONIZATION = 2;

    /**
     * 锁定状态枚举
     */
    public enum LockStatusEnum {
        LOCK("1"), // 锁定
        UNLOCK("0");// 未锁定
        private String value;

        LockStatusEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * 定位状态枚举
     */
    public enum LocationStatusEnum {
        HAVE_LOCATION(1), // 已定位
        HAVE_NOT_LOCATION(0), // 未定位
        ALL(-1);// 所有
        private int value;

        LocationStatusEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

    }

    public static final int loginstatus_index = 0; // 登录缓存

    public static final int car_index = 1; // 车辆分组

    public static final int car_plate = 2;

    public static final int business_index = 3; // 车组

    public static final int business_car_index = 4;

    public static final int terminal_index = 5;// 终端

    public static final int terminal_car_index = 6;//

    public static final int basicdata_index = 7;

    public static final int gps_index = 8;

    public static final int car_terminal_index = 9;// 通过车id找终端

    public static final int car_employee_index = 10;// 车辆找司机缓存

    public static final int employee_index = 11;

    public static final int code_phone = 12;

    public static final int traffic_rule = 13;

    public static final int gps_app = 14;

    public static final int avg_oil_index = 15;

    public static final int ERROR_CODE = 506;

    /**
     * 通用应答结果集 ResponseResult
     *
     * @author wenya
     */
    public enum ResponseResult {

        // success("成功", 0),
        // failure("失败", 1),
        // 与sl版本一致,暂时只处理成功，失败情况
        failure("失败", 0), success("成功", 1), messageerror("消息有误", 2), notsupport("终端不支持", 3), alarmhandle("报警处理确认", 4), offline(
                "终端离线", 5), noRegister("终端未注册", 6),
        // ecuSuccess("ECU成功",7),
        // ecuFailure("ECU失败",8),
        // ecuNotSupport("ECU不支持",9),
        typeError("操作类型无效", 10), limitError("限制类型无效", 11), vehicleClose("车辆关机", 12), communicateError("总线通信异常", 13), waiting(
                "等待未响应", -3), executing("正在执行", -2), outdate("已失效", -1), unlimited("不限", -4);

        private ResponseResult(String name, int value) {
            this.name = name;
            this.value = value;
        }

        private String name;

        private int value;

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        ResponseResult(String name) {
            this.name = name;
        }

        ResponseResult(int value) {
            this.value = value;
        }

    }

    // ecu异常码
    public enum ECUErrorCode {
        normal("正常", 0), paramlost("MPU下发的命令参数结构缺失", 1), synchro("当前处于等待同步状态", 2), cachefull("命令缓存已满", 3), queryouttime(
                "查询超时", 4), handshakefailure("重新握手当前执行命令返回失败", 5), outmaxcount("底层重发次数超过最大次数", 6), waitlock("进入等待锁车结果状态", 7), waitcanlelock(
                "进入等待解锁结果状态", 8), handshakecheck("握手校验失败", 9), prelock("当前处于软锁车状态", 10), cancletamper("当前处于防拆关闭状态", 11), ecufailure(
                "ECU返回结果为失败", 12), gpsidzero("MPU下发参数中密钥和GPS ID全为0", 13), unknown("未知", 225);

        private ECUErrorCode(String name, int value) {
            this.name = name;
            this.value = value;
        }

        private String name;

        private int value;

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        ECUErrorCode(String name) {
            this.name = name;
        }

        ECUErrorCode(int value) {
            this.value = value;
        }
    }

    /**
     * 报警提醒类型
     *
     * @author wenya (1:出区域限速 2:滞留超时3:一体机拆出报警4:车辆锁车报警5:ID不匹配报警)
     */
    public enum AlarmRemindType {

        inOutAreaAlarm("出区域限速", 1), retainedTimeoutAlarm("滞留超时", 2), terminalRemoveAlarm("一体机拆出", 3), carLockAlarm(
                "车辆锁车", 4), dNotMatchAlarm("ID不匹配", 5);

        private AlarmRemindType(String name, int value) {
            this.name = name;
            this.value = value;
        }

        private String name;

        private int value;

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        AlarmRemindType(String name) {
            this.name = name;
        }

        AlarmRemindType(int value) {
            this.value = value;
        }

    }

    /**
     * 车辆行驶状态枚举 CarStatue 在线行驶111(7)，在线停车011(3)，在线不定位01(1)，不在线行驶110(6),不在线停车010(2)，不在线不定位00(0) 第一位表示在线状态（0：不在线，1：在线）
     * 第二位表示定位状态（0：不定位，1：定位） 第三位表示行驶状态（0：停车，1：行驶）
     *
     * @author wenya
     */
    public enum CarState {

        VehicleStatusOnline("在线行驶", 7), VehicleStatusOnlineStop("在线停车", 3), VehicleStatusOnlineInvalid("在线不定位", 1), VehicleStatusOffline(
                "不在线行驶", 6), VehicleStatusOfflineStop("不在线停车", 2), VehicleStatusOfflineInvalid("不在线不定位", 0);

        private CarState(String name, int value) {
            this.name = name;
            this.value = value;
        }

        private String name;

        private int value;

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        CarState(String name) {
            this.name = name;
        }

        CarState(int value) {
            this.value = value;
        }

    }
    /**
     *  车辆品系
     */
    public enum CarType {

        CarType1("专用车品系", 5239),
        CarType2("载货车品系", 5240),
        CarType3("牵引车品系", 5241),
        CarType4("工程车品系", 5242)  ;

        private CarType(String name, int value) {
            this.name = name;
            this.code = value;
        }

        private String name;

        private Integer code;

        public String getName() {
            return name;
        }

        public Integer getCode() {
            return code;
        }


    }
    // 方向枚举
    public enum Direction {
        NORTH("北"), SOUTH("南"), EAST("东"), WEST("西"), NORTHEAST("东北"), NORTHWEST("西北"), SOUTHEAST("东南"), SOUTHWEST("西南");

        private String value;

        public String getValue() {
            return value;
        }

        Direction(String value) {
            this.value = value;
        }
    }

    // 系统日志操作类型
    public enum HandleType {
        add("增加", 1), update("修改", 2), delete("删除", 3), query("查询", 4);

        private HandleType(String name, int value) {
            this.name = name;
            this.value = value;
        }

        private final String name;

        private final int value;

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }
    }


    // 状态位
    public static final String ACC_OPEN = "ACC开,";

    public static final String ACC_CLOSE = "ACC关,";

    public static final String QIANMEN_OPEN = "前门开,";

    public static final String QIANMEN_CLOSE = "前门关,";

    public static final String ZHONGMEN_OPEN = "中门开,";

    public static final String ZHONGMEN_CLOSE = "中门关,";

    public static final String HOUMEN_OPEN = "后门开,";

    public static final String HOUMEN_CLOSE = "后门关,";

    public static final String DRIVER_OPEN = "驾驶门开,";

    public static final String DRIVER_CLOSE = "驾驶门关,";

    public static final String OFFLINE = "离线";

    public static final String ONLINE = "在线";



    /**
     * 是否重置redis失效时间枚举
     */
    public enum RedisLiveTimeReSetEnum {
        NO_RESET("0", "不重置"), // 不重置
        RESET("1", "重置"); // 重置
        private String code;

        private String value;

        RedisLiveTimeReSetEnum(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * 防控状态枚举
     */
    public enum PreventionStatusEnum {
        NOT_ACTIVE(0, "未激活"), ACTIVE(2, "已激活"), LOCKED(3, "已锁车"), BEING_ACTIVE(4, "正在激活"), BEING_CANCEL_ACTIVE(5,
                "正在取消激活"), BEING_LOCK(6, "正在锁车"), BEING_UNLOCK(7, "正在解锁"), UNKNOWN(10, "");

        private int code;

        private String msg;

        private static Map<Integer, PreventionStatusEnum> codes = new ConcurrentHashMap<>();

        static {
            for (PreventionStatusEnum statusEnum : PreventionStatusEnum.values()) {
                codes.put(statusEnum.getCode(), statusEnum);
            }
        }

        PreventionStatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        public static PreventionStatusEnum valueOf(int code) {
            if (codes.get(code) != null) {
                return codes.get(code);
            } else {
                return UNKNOWN;
            }
        }
    }

    /**
     * 确认是否登陆踢掉之前登陆者枚举
     */
    public enum ConfirmLoginFlgEnum {
        IS_LOGIN("0", "提示是否登陆"), // 提示是否登陆
        CONFIRM_LOGIN("1", "确认登陆"); // 确认登陆
        private String code;

        private String value;

        ConfirmLoginFlgEnum(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * 区域类型枚举
     */
    public enum AreaTypeEnum {
        STATION(11), // 一级服务站
        SUBSTATION(12), // 二级服务站
        TEAM(21), // 一级经销商
        SUB_TEAM(22);// 二级经销

        private Integer value;

        AreaTypeEnum(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    // 不替换单引号
    public static final String NO_REPLACE_SINGLE_QUOTATION = "NO_REPLACE_SINGLE_QUOTATION_";

    /**
     * 同步模式枚举
     */
    public enum SyncModelEnum {
        ADD(0),
        UPDATE(1),
        DELETE(2);
        private int value;

        SyncModelEnum(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

    /**
     * 同步操作结果枚举
     */
    public enum SyncOperateEnum {
        SUCCESS(0),
        ADD(1),
        DELETE(2),
        SWITCH_OFF(3),
        SWITCH_ON(4),
        UPDATE(5),//只更新二级网点(新增或更新)
        UPDATE_AND_DELETE(6);//更新且删除二级网点
        private int value;

        SyncOperateEnum(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }


    /**
     * 密集采集数据导出
     */
    public enum TimelyEnum{
        YES("是",0),
        NO("否",1),
        OPEN("开",0),
        CLOSE("关",1),
        UNKNOW("未知",9999);
        private String code;
        private int value;

        TimelyEnum(String code, int value) {
            this.code = code;
            this.value = value;
        }

        public String code() {
            return this.code;
        }

        public int value() {
            return this.value;
        }

        }

    /**
     * 刹车信号
     */
    public enum BrakeSignal{
        NO(0,"松开"),
        YES(1,"踩下"),
        ERROR(2,"出错"),
        UNKNOW(3,"不可用");
        private int code;
        private String value;
        private static Map<Integer, String> codes = new HashMap<Integer, String>();

        static {
            for (BrakeSignal signal : BrakeSignal.values()) {
                codes.put(signal.code(), signal.value);
            }
        }

        BrakeSignal(int code, String value ) {
            this.code = code;
            this.value = value;
        }

        public int code() {
            return this.code;
        }

        public String value() {
            return this.value;
        }

        public static String valueOf(int code) {
            if (codes.get(code) != null) {
                return codes.get(code);
            } else {
                return "";
            }
        }
    }

    /**
     * 空调开关信号
     */
    public enum AirSignal{
        NO(0,"关"),
        YES(1,"开");
        private int code;
        private String value;
        private static Map<Integer, String> codes = new HashMap<Integer, String>();

        static {
            for (AirSignal signal : AirSignal.values()) {
                codes.put(signal.code(), signal.value);
            }
        }

        AirSignal(int code, String value ) {
            this.code = code;
            this.value = value;
        }

        public int code() {
            return this.code;
        }

        public String value() {
            return this.value;
        }

        public static String valueOf(int code) {
            if (codes.get(code) != null) {
                return codes.get(code);
            } else {
                return "";
            }
        }
    }

    /**
     * 离合器开关
     */
    public enum ClutchSwitchSignal{
        NO(0,"离合器踏板被松开"),
        YES(1,"离合器踏板被踩下"),
        ERROR(2,"出错"),
        UNKNOW(3,"不可用");
        private int code;
        private String value;
        private static Map<Integer, String> codes = new HashMap<Integer, String>();

        static {
            for (ClutchSwitchSignal signal : ClutchSwitchSignal.values()) {
                codes.put(signal.code(), signal.value);
            }
        }

        ClutchSwitchSignal(int code, String value ) {
            this.code = code;
            this.value = value;
        }

        public int code() {
            return this.code;
        }

        public String value() {
            return this.value;
        }

        public static String valueOf(int code) {
            if (codes.get(code) != null) {
                return codes.get(code);
            } else {
                return "";
            }
        }
    }

    /**
     * 制动状态
     */
    public enum BrakingState{
        INVALID(0,"无效"),
        ON(1,"打开"),
        OFF(2,"关闭");
        private int code;
        private String value;
        private static Map<Integer, String> codes = new HashMap<Integer, String>();

        static {
            for (BrakingState signal : BrakingState.values()) {
                codes.put(signal.code(), signal.value);
            }
        }

        BrakingState(int code, String value ) {
            this.code = code;
            this.value = value;
        }

        public int code() {
            return this.code;
        }

        public String value() {
            return this.value;
        }

        public static String valueOf(int code) {
            if (codes.get(code) != null) {
                return codes.get(code);
            } else {
                return "";
            }
        }
    }

    /**
     * 空挡信号
     */
    public enum Neutral{
        INVALID(0,"无效"),
        ON(1,"空挡"),
        OFF(2,"非空挡");
        private int code;
        private String value;
        private static Map<Integer, String> codes = new HashMap<Integer, String>();

        static {
            for (Neutral signal : Neutral.values()) {
                codes.put(signal.code(), signal.value);
            }
        }

        Neutral(int code, String value ) {
            this.code = code;
            this.value = value;
        }

        public int code() {
            return this.code;
        }

        public String value() {
            return this.value;
        }

        public static String valueOf(int code) {
            if (codes.get(code) != null) {
                return codes.get(code);
            } else {
                return "";
            }
        }
    }

    /**
     * 问题与建议 状态
     */
    public enum QsState {

        create("待回复", 11101), finish("已回复", 11102);

        QsState(String name, int value) {
            this.name = name;
            this.value = value;
        }

        private String name;

        private int value;

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }



    }
}


