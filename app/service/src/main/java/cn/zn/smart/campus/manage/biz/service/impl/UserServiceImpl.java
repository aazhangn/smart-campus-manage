package cn.zn.smart.campus.manage.biz.service.impl;

import cn.zn.smart.campus.manage.biz.dto.AdminiDTO;
import cn.zn.smart.campus.manage.biz.dto.WeChatUserDTO;
import cn.zn.smart.campus.manage.biz.exception.BizException;
import cn.zn.smart.campus.manage.biz.exception.ErrorEnum;
import cn.zn.smart.campus.manage.biz.service.UserService;
import cn.zn.smart.campus.manage.dao.po.Administrator;
import cn.zn.smart.campus.manage.dao.po.WeChatUser;
import cn.zn.smart.campus.manage.dao.service.IAdministratorService;
import cn.zn.smart.campus.manage.dao.service.IWeChatUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/20 21:00
 */
public class UserServiceImpl implements UserService {
    @Resource
    private IWeChatUserService iWeChatUserService;
    @Resource
    private IAdministratorService iAdministratorService;
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
    public boolean loginCms(String username, String pwd) {
        if (StringUtils.isBlank(username)||StringUtils.isBlank(pwd)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        QueryWrapper<Administrator> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username).eq("password",pwd);
        Administrator admi = iAdministratorService.getOne(wrapper);
        return !Objects.isNull(admi);
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
}
