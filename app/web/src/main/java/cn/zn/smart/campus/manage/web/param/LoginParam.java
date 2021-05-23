package cn.zn.smart.campus.manage.web.param;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/21 23:24
 */
public class LoginParam implements Serializable {
    private static final long serialVersionUID = -8423357386468603118L;

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
