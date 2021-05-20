package cn.zn.smart.campus.manage.biz.service;

import cn.zn.smart.campus.manage.biz.dto.AdminiDTO;
import cn.zn.smart.campus.manage.biz.dto.WeChatUserDTO;
import cn.zn.smart.campus.manage.dao.po.WeChatUser;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/20 20:51
 */
public interface UserService {
    /**
     * 微信小程序用户是否已注册
     * @param openid
     * @return
     */
    public WeChatUser isRegister(String openid);

    /**
     * 更新小程序用户信息
     * @param weChatUserDTO
     * @return
     */
    public boolean updateWeChatUser(WeChatUserDTO weChatUserDTO);

    /**
     * 后台管理员登录
     * @param username
     * @param pwd
     * @return
     */
    public boolean loginCms(String username, String pwd);

    /**
     * 更新管理员信息
     * @param adminiDTO
     * @return
     */
    public boolean updateAdmin(AdminiDTO adminiDTO);
}
