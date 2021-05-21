package cn.zn.smart.campus.manage.biz.enums.assignment;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/21 16:15
 */
public enum AssignmentIsObjectiveEnum {
    /**
     * 是否客观题
     */
    OBJECTIVE(1),
    SUBJECTIVE(0);

    private final Integer value;

    AssignmentIsObjectiveEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
