package com.example.common.vo;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author TH
 */

public enum ReturnCode {
    /** Return Code */
    CODE_4001("InvalidPartnerCode", "PartnerCode does not exist.","Please check your partnerCode."),
    CODE_4002("InvalidAccountInformation", "UserAccount and password are not correct.","Please check your userAccount and password."),
    CODE_4004("InsufficientParameter", "System couldn't get all of required parameter.","Please check API document."),
    SOH_RESULT_TARGET_ERROR("TargetInvalidError","the data associated with the target is invalid.","you have to specify valid target."),
    SOH_RESULT_PARAMETER_ERROR("ParameterInvalidError","the data associated with the parameter is invalid.","you have to specify valid parameter."),
    API_PERMITTION_ERROR("ApiPermittionError","you don't have permittion to call this API.","Please check your privilege."),
    INVALID_AUTHKEY_ERROR("invalidAuthkey","authkey is not valid.","Please get valid authkey."),
    BATTERY_ERROR_TARGET_ERROR("TargetInvalidError","The data associated with the target is invalid.","You have to specify valid target."),
    BATTERY_ERROR_TARGET_MODEL_ERROR("DataNotFoundError","The data associated with the model could not be found.","You have to specify valid model."),
    BATTERY_ERROR_TARGET_ID_ERROR("DataNotFoundError","The data associated with the id could not be found.","You have to specify valid id."),
    PARAMETER_INSUFFICIENT_ERROR("insufficientParameter","system couldn't get all of required parameter.","Please check API document."),
    DATADUPLICATE_ERROR("DataDuplicateError","the data which you want to regist has found in database.","you cannot regist duplicated data to database."),
    BATTERY_ERROR_PERIOD_FORMAT_ERROR("DateFormatError","the date format is yyyy-MM-dd.","you have to specify valid date format."),
    BATTERY_ERROR_PERIOD_INTERVAL_ERROR("PeriodIntervalError","the from of period is greater than the to of period.","you have to specify valid period interval."),
    BATTERY_ERROR_LEVEL_ERROR("ErrorLevelError","the data associated with the error_level is invalid.","you have to specify valid error_level."),
    PARAMETER_NOT_FOUND_ERROR("ParameterNotFoundError","The parameter cannot be found.","You have to specify parameter."),
    UNKNOWN_ERROR("", "","");
    /**
     * ************************************************
     * 业务错误提示 Begin
     * **********************************************************************
     */

    /*************************************************** 业务错误提示 End ************************************************************************/

    private String code;
    private String message;
    private String tips;
    private static Map<String, ReturnCode> codes = new ConcurrentHashMap<String, ReturnCode>();

    static {
        for (ReturnCode errorCodeEnum : ReturnCode.values()) {
            codes.put(errorCodeEnum.code(), errorCodeEnum);
        }
    }

    ReturnCode(String code) {
        this.code = code;
    }

    ReturnCode(final String code, final String message, final String tips) {
        this.code = code;
        this.message = message;
        this.tips = tips;
    }

    public String tips() {return tips;};
    public String code() {
        return code;
    }
    public String message() {
        return message;
    }

    public static ReturnCode valueOf(int code) {
        if (codes.get(code) != null) {
            return codes.get(code);
        } else {
            return UNKNOWN_ERROR;
        }
    }

}
