package cn.zn.smart.campus.manage.dao.exception;

/**
 * @Description: 异常枚举
 * @Author: zhangnan
 * @Date: 2021/05/14 20:17
 */
public enum ExceEnum {
    /**
     *
     */
    SYS_PARAM_EXCE("SC-0001", "参数异常");
    /**
     * 错误码
     */
    private final String code;
    /**
     * 错误信息
     */
    private final String msg;

    ExceEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static ExceEnum getErrorByCode(String code) {
        for (ExceEnum exceEnum : values()) {
            if (exceEnum.getCode().equals(code)) {
                return exceEnum;
            }
        }
        return null;
    }
}
