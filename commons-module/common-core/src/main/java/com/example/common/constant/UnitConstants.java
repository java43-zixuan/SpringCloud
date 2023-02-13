package com.example.common.constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 数据粒度单位
 *
 * @author wangjialing
 * @date 2020/11/19 17:20
 */
public interface UnitConstants<T> {

    String HBASE_HISTORY_DATA_INFO_TABLE="historyDataTimeComplete";
    String HBASE_HISTORY_DATA_INFO_FAMILY="online_time";
    String HBASE_VEHICLE_INFO_TABLE="vehicleInfo";
    String HBASE_VEHICLE_INFO_FAMILY="vehicle";
    String HBASE_VEHICLE_INFO_FIELD_PC="partner_code";
    String HBASE_VEHICLE_INFO_FIELD_VIN="vin";
    String HBASE_VEHICLE_INFO_FIELD_VI="vehicle_id";
    String HBASE_VEHICLE_INFO_FIELD_BI="battery_id";
    String HBASE_VEHICLE_INFO_FIELD_VIF="vehicle_invalid_flag";
    String CHARACTER_COMMA=",";
    String CHARACTER_HTML="html";
    String PREPROCESS_REQ_CALL_BACK = "preprocess-req";
    String BATTERY_ANALYSIS_REQ_CALL_BACK = "battery-analysis-req";
    /**
     * 删除区分：0未删除
     */
    int IS_NOT_DEL = 0;
    /**
     *删除区分：1已删除
     */
    int HAVE_DEL = 1;

    String PANASONIC_CODE = "code";
    String PANASONIC_SUBCODE = "subcode";
    String PANASONIC_MESSAGE = "message";
    String PANASONIC_RESULT = "result";
    String PANASONIC_REQUEST_ID = "request_id";
    String RESULT_ERROR = "ERROR";
    String RESULT_FAIL_CODE = "500";
    String BLANK = "";
    Integer EXECUTE_SUCCESS = 1;
    Integer EXECUTE_FAIL = 0;
    Integer GENERAL_CODE_0 = 0;
    Integer GENERAL_CODE_1 = 1;
    Integer GENERAL_CODE_2 = 2;
    Integer INITIAL_CAPACITY = 16;
    String SX_SUBCODE_WARNING = "W-";
    String SX_SUBCODE_ERROR = "Error";
    String SX_NO_ERROR_CODE = "-";
    String SUCCESS = "Success";

    String SYSTEM_EXCEPTION_MESSAGE = "error";
    String ERROR_OCCURED = "ErrorOccured";

    String DATA_PROCESSING_FLAG_UNTREATED= "0";
    String DATA_PROCESSING_FLAG_SUCCESS = "1";
    String DATA_PROCESSING_FLAG_FAIL = "2";
    String DATA_PROCESSING_FLAG_PROCESSING = "3";

    String STATE_ERROR = "E";
    String STATE_WARNING = "W";
    String STATE_NORMAL = "N";
    String STATE_UNTREATED = "-";
    String SX_SUBCODE_INFO = "I-";

    Integer INVALID_PARTNER_CODE = 4001;
    String ERROR_CODE_4001 = "InvalidPartnerCode";
    String ERROR_MESSAGE_4001 = "PartnerCode does not exist.";
    String TIPS_4001 = "Please check your partnerCode.";

    Integer CODE_4002 = 4002;
    String ERROR_CODE_4002 = "InvalidAccountInformation";
    String ERROR_MESSAGE_4002 = "UserAccount and password are not correct.";
    String TIPS_4002 = "Please check your userAccount and password.";

    Integer CODE_4004 = 4004;
    String ERROR_CODE_4004 = "InsufficientParameter";
    String ERROR_MESSAGE_4004 = "System couldn't get all of required parameter.";
    String TIPS_4004 = "Please check API document.";

    Integer INVALID_AUTHKEY = 4009;
    Integer TARGET_INVALID_RESPONSE_CODE = 4008;
    Integer TARGET_NULL_RESPONSE_CODE = 4004;
    Integer TARGET_ERROR_RESPONSE_CODE = 4005;
    Integer PARAMETER_ERROR_RESPONSE_CODE = 4009;
    Integer PERIOD_ERROR_RESPONSE_CODE = 4010;
    Integer ERROR_LEVEL_ERROR_RESPONSE_CODE = 4011;
    Integer SOH_RESULT_ERROR_RESPONSE_CODE = 4012;
    Integer SEARCH_TARGET_PARAM_ERROR=4026;
    Integer SEARCH_TERM_PARAM_ERROR=4027;
    Integer SEARCH_CONDITION_PARAM_ERROR=4028;
    Integer SEARCH_PARAMETER_NONE_ERROR=4029;
    Integer SEARCH_TERM_PARAM_VALUE_ERROR=4040;
    String SOH_RESULT_TARGET_ERROR_CODE = "TargetInvalidError";
    String SOH_RESULT_TARGET_ERROR_MESSAGE = "the data associated with the target is invalid.";
    String SOH_RESULT_TARGET_ERROR_TIPS = "you have to specify valid target.";
    String SOH_RESULT_PARAMETER_ERROR_CODE = "ParameterInvalidError";
    String SOH_RESULT_PARAMETER_ERROR_MESSAGE = "the data associated with the parameter is invalid.";
    String SOH_RESULT_PARAMETER_ERROR_TIPS = "you have to specify valid parameter.";

    String APIPERMITTIONERROR_CODE = "ApiPermittionError";
    String APIPERMITTIONERROR_MESSAGE = "you don't have permittion to call this API.";
    String APIPERMITTIONERROR_TIPS = "Please check your privilege.";


    String INVALID_AUTHKEY_ERROR_CODE = "invalidAuthkey";
    String INVALID_AUTHKEY_ERROR_MESSAGE = "authkey is not valid.";
    String INVALID_AUTHKEY_ERROR_TIPS = "Please get valid authkey.";


    String BATTERY_ERROR_TARGET_ERROR_CODE = "TargetInvalidError";
    String BATTERY_ERROR_TARGET_ERROR_MESSAGE = "The data associated with the target is invalid.";
    String BATTERY_ERROR_TARGET_ERROR_TIPS = "You have to specify valid target.";


    String BATTERY_ERROR_TARGET_ERROR_CODE2 = "DataNotFoundError";
    String BATTERY_ERROR_TARGET_ERROR_MESSAGE2 = "The data associated with the model could not be found.";
    String BATTERY_ERROR_TARGET_ERROR_TIPS2 = "You have to specify valid model.";

    String BATTERY_ERROR_TARGET_ERROR_MESSAGE3 = "The data associated with the id could not be found.";
    String BATTERY_ERROR_TARGET_ERROR_TIPS3 = "You have to specify valid id.";

    String PARAMETER_INSUFFICIENT_ERROR_CODE = "insufficientParameter";
    String PARAMETER_INSUFFICIENT_ERROR_MESSAGE = "system couldn't get all of required parameter.";
    String PARAMETER_INSUFFICIENT_ERROR_TIPS = "Please check API document.";

    String DATADUPLICATE_ERROR_CODE = "DataDuplicateError";
    String DATADUPLICATE_ERROR_MESSAGE = "the data which you want to regist has found in database.";
    String DATADUPLICATE_ERROR_TIPS = "you cannot regist duplicated data to database.";



    String BATTERY_ERROR_PERIOD_FORMAT_ERROR_CODE = "DateFormatError";
    String BATTERY_ERROR_PERIOD_FORMAT_ERROR_MESSAGE = "the date format is yyyy-MM-dd.";
    String BATTERY_ERROR_PERIOD_FORMAT_ERROR_TIPS = "you have to specify valid date format.";
    String BATTERY_ERROR_PERIOD_INTERVAL_ERROR_CODE = "PeriodIntervalError";
    String BATTERY_ERROR_PERIOD_INTERVAL_ERROR_MESSAGE = "the from of period is greater than the to of period.";
    String BATTERY_ERROR_PERIOD_INTERVAL_ERROR_TIPS = "you have to specify valid period interval.";
    String BATTERY_ERROR_LEVEL_ERROR_CODE = "ErrorLevelError";
    String BATTERY_ERROR_LEVEL_ERROR_MESSAGE = "the data associated with the error_level is invalid.";
    String BATTERY_ERROR_LEVEL_ERROR_TIPS = "you have to specify valid error_level.";

    Integer SUCCESS_CODE = 200;
    Integer FAIL_CODE = 500;
    Integer FAIL_BIND_CODE = 501;
    Integer FAIL_VALIDATE_CODE = 502;
    Integer TIMEOUT_CODE = 506;
    Integer RESPONSE_SUCCESS_CODE = 2000;

    String BATTERY_ID_NORMAL = "battery_id";
    String BATTERY_MODEL_NORMAL = "battery_model";
    Integer ERROR_CHAR_LENTH = 1500;
    Integer EXECUTE_MAX_TIME = 3;

    /**
     * date String Convert To Zone
     * @param dateStr date String
     * @param oldFormat old Format
     * @param newFormat new Format
     * @return Zone
     * @throws ParseException ParseException
     */
    static String dateStrConvertToZone(String dateStr, String oldFormat, String newFormat) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(oldFormat);
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(newFormat);
        return simpleDateFormat1.format(simpleDateFormat.parse(dateStr));
    }

    /** openapi TokenKey */
    String TOKEN_KEY = "BatteryCartridge";

}
