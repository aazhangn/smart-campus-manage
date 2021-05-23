package cn.zn.smart.campus.manage.biz.service;

import cn.zn.smart.campus.manage.biz.dto.AdminiDTO;
import cn.zn.smart.campus.manage.biz.dto.WeChatUserDTO;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.Administrator;
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
    public Administrator loginCms(String username, String pwd);

    /**
     * 更新管理员信息
     * @param adminiDTO
     * @return
     */
    public boolean updateAdmin(AdminiDTO adminiDTO);

    /**
     * 获取管理员信息
     * @param adminId
     * @return
     */
    public Administrator getAdmin(String adminId);

    /**
     * 添加管理员
     * @param teacherId
     * @return
     */
    public boolean saveAdmin(String teacherId);

    /**
     * 删除管理员
     * @param administratorId
     * @return
     */
    public boolean deleteAdmin(String administratorId);

    /**
     * 分页查询
     * @param page
     * @return
     */
    public ResultPage<Administrator> listAdmin(QueryPage page);

}
