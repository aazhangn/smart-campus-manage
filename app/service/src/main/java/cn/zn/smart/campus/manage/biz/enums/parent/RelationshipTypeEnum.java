package cn.zn.smart.campus.manage.biz.enums.parent;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/21 17:08
 */
public enum RelationshipTypeEnum {
    /**
     * 亲属关系类型
     */
    FATHER("父亲"),
    MATHER("母亲"),
    OTHER("其他");

    private final String value;

    RelationshipTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
