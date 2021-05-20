package cn.zn.smart.campus.manage.biz.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/20 20:58
 */
@Data
public class WeChatUserDTO implements Serializable {

    private static final long serialVersionUID = 6297769379784556169L;
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
