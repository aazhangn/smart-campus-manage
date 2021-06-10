package cn.zn.smart.campus.manage.biz.enums.user;

/**
 * @Description: 用户角色枚举
 * @Author: zhangnan
 * @Date: 2021/05/26 13:42
 */
public enum UserRoleEnum {
    /**
     * 用户角色
     */
    //管理员角色 必然是教师
    ADMIN("admin","管理员"),
    TEACHER("teacher","教师"),
    PARENT("parent","家长"),
    STUDENT("student","学生");

    private final String value;
    private final String desc;

    UserRoleEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
