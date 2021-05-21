package cn.zn.smart.campus.manage.biz.enums.assignment;

/**
 * @Description: 错题掌握情况枚举
 * @Author: zhangnan
 * @Date: 2021/05/21 19:20
 */
public enum MesteryStatusEnum {
    /**
     * 错题掌握情况
     */
    NOT_FAMILIAR(0,"完全不熟悉"),
    HALF_FAMILIAR(1,"掌握一半"),
    COMPLETE(2,"完全掌握");
    private Integer status;
    private String desc;

    MesteryStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
