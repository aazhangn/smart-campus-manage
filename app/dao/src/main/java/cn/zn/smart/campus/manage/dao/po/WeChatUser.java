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
 * @since 2021-05-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WeChatUser extends BaseLogicDeletePo {

    private static final long serialVersionUID = 1L;

    /**
     * open_id
     */
    private String openId;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像url
     */
    private String headPicUrl;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 角色
     */
    private String role;

    /**
     * 教师、学生、家长id
     */
    private String mapId;


}
