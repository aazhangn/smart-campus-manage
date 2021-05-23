package cn.zn.smart.campus.manage.biz.service.impl;

import cn.zn.smart.campus.manage.biz.dto.AdminiDTO;
import cn.zn.smart.campus.manage.biz.dto.WeChatUserDTO;
import cn.zn.smart.campus.manage.biz.exception.BizException;
import cn.zn.smart.campus.manage.biz.exception.ErrorEnum;
import cn.zn.smart.campus.manage.biz.service.UserService;
import cn.zn.smart.campus.manage.dao.po.Administrator;
import cn.zn.smart.campus.manage.dao.po.TeacherInfo;
import cn.zn.smart.campus.manage.dao.po.WeChatUser;
import cn.zn.smart.campus.manage.dao.service.IAdministratorService;
import cn.zn.smart.campus.manage.dao.service.ITeacherInfoService;
import cn.zn.smart.campus.manage.dao.service.IWeChatUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
