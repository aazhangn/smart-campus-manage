package cn.zn.smart.campus.manage.biz.exception;

/**
 * @Description: 异常枚举
 * @Author: zhangnan
 * @Date: 2021/05/13 23:57
 */
public enum ErrorEnum {

    /**
     *错误码说明：
     * <p>
     * SC开头：smart-campus应用
     * <p>
     * 第1位：业务错误：0；系统错误：1
     * <p>
     * 后三位：错误码，000表示成功，逻辑错误从001往上加；系统相关从999往下减
     */
    SUCCESS("SC-1000", "Success"),
    SYS_PARAM_ERROR("SC-1001","非法参数"),
    SYS_QUERY_DATA_IS_NULL("SC-1002","查询数据为空"),
    SYS_ILLEGAL_OPERATION("SC-1003","非法操作");
    /**
     * 错误码
     */
    private final String code;
    /**
     * 错误信息
     */
    private final String msg;

    ErrorEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static ErrorEnum getErrorByCode(String code) {
        for (ErrorEnum errorEnum : values()) {
            if (errorEnum.getCode().equals(code)) {
                return errorEnum;
            }
        }
        return null;
    }
}
