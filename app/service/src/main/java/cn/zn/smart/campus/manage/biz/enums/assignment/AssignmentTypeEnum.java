package cn.zn.smart.campus.manage.biz.enums.assignment;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/18 21:35
 */
public enum AssignmentType {
    /**
     * 题型：第一位：0客观、1主观
     */
    COMPLETION("填空","01"),
    ONE_CHOICE("单选","02-1"),
    MUL_CHOICE("多选","02-2"),
    TRUE_OR_FALSE("判断","03"),
    SHORT_ANSWER("简答","11"),
    DISCUSS("论述","12");
    /**
     * 值
     */
    private final String value;
    /**
     * 编号
     */
    private final String no;

    AssignmentType(String value, String no) {
        this.value = value;
        this.no = no;
    }

    public String getValue() {
        return value;
    }

    public String getNo() {
        return no;
    }
}
