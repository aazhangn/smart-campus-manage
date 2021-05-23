package cn.zn.smart.campus.manage.biz.enums.notice;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/23 12:31
 */
public enum ReceivePeopleRoleEnum {
    /**
     * role
     */
    TEACHER("teacher"),
    STUDENT("student"),
    PARENT("parent");

    private final String role;

    ReceivePeopleRoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
