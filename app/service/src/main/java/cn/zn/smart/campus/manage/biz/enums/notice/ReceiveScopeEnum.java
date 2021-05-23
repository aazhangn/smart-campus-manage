package cn.zn.smart.campus.manage.biz.enums.notice;

/**
 * @Description: 消息接收范围枚举
 * @Author: zhangnan
 * @Date: 2021/05/23 11:57
 */
public enum ReceiveScopeEnum {
    /**
     * 消息接收范围
     */
    ALL("all"),
    GRADE("grade"),
    CLASS("class");

    private final String value;

    ReceiveScopeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
