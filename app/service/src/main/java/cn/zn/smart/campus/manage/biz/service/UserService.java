package cn.zn.smart.campus.manage.biz.service;

import cn.zn.smart.campus.manage.biz.dto.AdminiDTO;
import cn.zn.smart.campus.manage.biz.dto.UserDTO;
import cn.zn.smart.campus.manage.biz.dto.WeChatUserDTO;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.Administrator;
import cn.zn.smart.campus.manage.dao.po.UserInfo;
import cn.zn.smart.campus.manage.dao.po.WeChatUser;

import java.util.List;

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

    /**
     * 添加用户
     * @param userDTO
     * @return
     */
    public boolean save(UserDTO userDTO);

    /**
     * 根据对应角色id删除
     * @param mapId
     * @return
     */
    public boolean deleteByMapId(String mapId);

    /**
     * 根据mapId批量删除
     * @param mapIdList
     * @return
     */
    public boolean deleteByMapIds(List<String> mapIdList);


    /**
     * 更新信息
     * @param username
     * @param pwd
     * @return
     */
    public UserInfo login(String username, String pwd);
    /**
     * 分页查询
     * @param page
     * @return
     */
    public ResultPage<UserInfo> listByPage(QueryPage page);

    /**
     * 更新信息
     * @param userDTO
     * @return
     */
    public boolean update(UserDTO userDTO);

    /**
     * 查询信息
     * @param userId
     * @return
     */
    public UserInfo get(String userId);
}
