package cn.zn.smart.campus.manage.biz.enums;

/**
 * @Description: 聘用类型枚举
 * @Author: zhangnan
 * @Date: 2021/05/17 17:45
 */
public enum HireTypeEnum {
    /**
     * 编制
     */
    PERMANENT("01","permanent"),
    /**
     * 合同制
     */
    CONTRACT("02","contract");

    /**
     * 编号
     */
    private final String no;
    /**
     * 描述
     */
    private final String desc;

    HireTypeEnum(String no, String desc) {
        this.no = no;
        this.desc = desc;
    }

    public String getNo() {
        return no;
    }

    public String getDesc() {
        return desc;
    }
}



