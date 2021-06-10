package cn.zn.smart.campus.manage.biz.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/26 13:34
 */
@Data
public class UserDTO implements Serializable{


    private static final long serialVersionUID = 3348251827542654742L;
    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 微信openid
     */
    private String weChatOpenid;

    /**
     * 对应工号/学号/家长ID
     */
    private String userMapId;

    /**
     * 角色
     */
    private String role;
}
