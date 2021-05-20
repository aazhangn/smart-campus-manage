package cn.zn.smart.campus.manage.biz.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/20 21:29
 */
@Data
public class AdminiDTO implements Serializable {

    private static final long serialVersionUID = -6084165929082583577L;
    /**
     * 管理员id
     */
    private String administratorId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 对应教师工号
     */
    private String teacherId;
}
