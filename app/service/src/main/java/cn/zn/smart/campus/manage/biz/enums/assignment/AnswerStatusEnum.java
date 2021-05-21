package cn.zn.smart.campus.manage.biz.enums.assignment;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/21 16:22
 */
public enum AnswerStatusEnum {
    /**
     * 作答状态
     */
    WAITING_MARK(0),
    FINISH_MARK(1);

    private final Integer value;

    AnswerStatusEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
