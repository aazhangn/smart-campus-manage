package cn.zn.smart.campus.manage.dao.po;

import cn.zn.smart.campus.manage.dao.po.base.BaseLogicDeletePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangnan
 * @since 2021-05-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserInfo extends BaseLogicDeletePo {

    private static final long serialVersionUID = 1L;

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
