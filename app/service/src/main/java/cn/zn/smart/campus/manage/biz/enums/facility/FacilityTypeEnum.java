package cn.zn.smart.campus.manage.biz.enums.facility;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/20 17:01
 */
public enum FacilityTypeEnum {
    /**
     * 设施类型
     */
    INDOOR_ROOM("场地-室内","11"),
    OUTDOOR_SPACE("场地-室外","12"),
    PORTABLE_MEETING_FACILITY("移动-会议设施","21"),
    PORTABLE_ACTIVITY_FACILITY("移动-活动设施","22");

    private String type;
    private String no;

    FacilityTypeEnum(String type, String no) {
        this.type = type;
        this.no = no;
    }

    public String getType() {
        return type;
    }

    public String getNo() {
        return no;
    }
}
