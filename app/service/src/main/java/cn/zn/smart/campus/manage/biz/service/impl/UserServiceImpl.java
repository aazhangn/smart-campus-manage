package cn.zn.smart.campus.manage.biz.service.impl;

import cn.zn.smart.campus.manage.biz.dto.AdminiDTO;
import cn.zn.smart.campus.manage.biz.dto.UserDTO;
import cn.zn.smart.campus.manage.biz.dto.WeChatUserDTO;
import cn.zn.smart.campus.manage.biz.exception.BizException;
import cn.zn.smart.campus.manage.biz.exception.ErrorEnum;
import cn.zn.smart.campus.manage.biz.service.UserService;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.Administrator;
import cn.zn.smart.campus.manage.dao.po.TeacherInfo;
import cn.zn.smart.campus.manage.dao.po.UserInfo;
import cn.zn.smart.campus.manage.dao.po.WeChatUser;
import cn.zn.smart.campus.manage.dao.service.IAdministratorService;
import cn.zn.smart.campus.manage.dao.service.ITeacherInfoService;
import cn.zn.smart.campus.manage.dao.service.IUserInfoService;
import cn.zn.smart.campus.manage.dao.service.IWeChatUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/20 21:00
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private IWeChatUserService iWeChatUserService;
    @Resource
    private IAdministratorService iAdministratorService;
    @Resource
    private ITeacherInfoService iTeacherInfoService;
    @Resource
    private IUserInfoService iUserInfoService;
    @Override
    public WeChatUser isRegister(String openid) {
        if (StringUtils.isBlank(openid)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        QueryWrapper<WeChatUser> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id",openid);
        return iWeChatUserService.getOne(wrapper);
    }

    @Override
    public boolean updateWeChatUser(WeChatUserDTO weChatUserDTO) {
        if (Objects.isNull(weChatUserDTO)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        if (StringUtils.isBlank(weChatUserDTO.getPhoneNumber())||StringUtils.isBlank(weChatUserDTO.getOpenId())){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR.getCode(),"缺失必要参数");
        }
        WeChatUser weChatUser = new WeChatUser();
        BeanUtils.copyProperties(weChatUserDTO,weChatUser);
        UpdateWrapper<WeChatUser> wrapper = new UpdateWrapper<>();
        wrapper.eq("phone_number",weChatUser.getPhoneNumber());
        return iWeChatUserService.update(weChatUser,wrapper);
    }

    @Override
    public Administrator loginCms(String username, String pwd) {
        if (StringUtils.isBlank(username)||StringUtils.isBlank(pwd)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        QueryWrapper<Administrator> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        Administrator admi = iAdministratorService.getOne(wrapper);
        if (admi.getPassword().equals(pwd)){
            return admi;
        }
        return null;
    }

    @Override
    public boolean updateAdmin(AdminiDTO adminiDTO) {
        if (Objects.isNull(adminiDTO)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        Administrator administrator = new Administrator();
        BeanUtils.copyProperties(adminiDTO,administrator);
        UpdateWrapper<Administrator> wrapper = new UpdateWrapper<>();
        wrapper.eq("administrator_id",adminiDTO.getAdministratorId());
        return iAdministratorService.update(administrator,wrapper);
    }

    @Override
    public Administrator getAdmin(String adminId) {
        if (StringUtils.isBlank(adminId)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iAdministratorService.getByEntityId(adminId,"administratorId");
    }

    @Override
    public boolean saveAdmin(String teacherId) {
        if (StringUtils.isBlank(teacherId)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        TeacherInfo teacherInfo = iTeacherInfoService.getByEntityId(teacherId,"teacherId");
        if (Objects.isNull(teacherInfo)){
            throw new BizException(ErrorEnum.SYS_QUERY_DATA_IS_NULL.getCode(),"该教师不存在");
        }
        Administrator admin = new Administrator();
        admin.setTeacherId(teacherId);
        //id
        admin.setAdministratorId("admin_"+teacherId);
        //职工号
        admin.setUsername(teacherId);
        //身份证后7-1位
        admin.setPassword(teacherInfo.getIdNumber().substring(teacherInfo.getIdNumber().length()-7,
                teacherInfo.getIdNumber().length()-1));
        return iAdministratorService.save(admin);
    }

    @Override
    public boolean deleteAdmin(String administratorId) {
        if (StringUtils.isBlank(administratorId)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iAdministratorService.remove(new QueryWrapper<Administrator>().eq("administrator_id",administratorId));
    }

    @Override
    public ResultPage<Administrator> listAdmin(QueryPage page) {
        if (Objects.isNull(page)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iAdministratorService.getEntityListByPage(page, null);
    }

    @Override
    public boolean save(UserDTO userDTO) {
        if (Objects.isNull(userDTO)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        if (StringUtils.isBlank(userDTO.getRole())||StringUtils.isBlank(userDTO.getUserMapId())||
                StringUtils.isBlank(userDTO.getPassword())){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR.getCode(),"必要参数(角色/密码/d对应ID)非法");
        }
        UserInfo user  = new UserInfo();
        BeanUtils.copyProperties(userDTO,user);
        //设置id和username
        //(适配家长用户名生成)
        if (StringUtils.isBlank(user.getUsername())){
            user.setUsername(userDTO.getUserMapId());
        }
        if (StringUtils.isBlank(user.getUserId())){
            user.setUserId("user_"+userDTO.getUserMapId());
        }

        return  iUserInfoService.save(user);
    }

    @Override
    public boolean deleteByMapId(String mapId) {
        if (StringUtils.isBlank(mapId)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iUserInfoService.remove(new QueryWrapper<UserInfo>().eq("user_map_id",mapId));
    }

    @Override
    public boolean deleteByMapIds(List<String> mapIdList) {
        if (CollectionUtils.isEmpty(mapIdList)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iUserInfoService.deleteBatchByEntityId(mapIdList,"userMapId");
    }

    @Override
    public UserInfo login(String username, String pwd) {
        if (StringUtils.isBlank(username)||StringUtils.isBlank(pwd)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        UserInfo user = iUserInfoService.getOne(new QueryWrapper<UserInfo>().eq("username",username));
        if (Objects.isNull(user)||!user.getPassword().equals(pwd)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR.getCode(),"用户名或密码错误");
        }
        return user;
    }

    @Override
    public ResultPage<UserInfo> listByPage(QueryPage page) {
        if (Objects.isNull(page)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iUserInfoService.getEntityListByPage(page,null);
    }

    @Override
    public boolean update(UserDTO userDTO) {
        if (Objects.isNull(userDTO)||StringUtils.isBlank(userDTO.getUserId())){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userDTO,userInfo);
        return iUserInfoService.update(userInfo,new UpdateWrapper<UserInfo>().eq("user_id",userDTO.getUserId()));
    }

    @Override
    public UserInfo get(String userId) {
        if (StringUtils.isBlank(userId)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iUserInfoService.getByEntityId(userId,"userId");
    }
}
