package cn.zn.smart.campus.manage.biz.enums.facility;

/**
 * @Description: 设施状态枚举
 * @Author: zhangnan
 * @Date: 2021/05/20 15:46
 */
public enum FacilityStatusEnum {
    /**
     * 设施状态
     */
    FREE("空闲","1"),
    BUSY("借用中","0");

    private final String status;
    private final String no;

    FacilityStatusEnum(String status, String no) {
        this.status = status;
        this.no = no;
    }

    public String getStatus() {
        return status;
    }

    public String getNo() {
        return no;
    }
}
