package cn.zn.smart.campus.manage.biz.service.impl;

import cn.zn.smart.campus.manage.biz.dto.TeacherDTO;
import cn.zn.smart.campus.manage.biz.dto.UserDTO;
import cn.zn.smart.campus.manage.biz.enums.user.UserRoleEnum;
import cn.zn.smart.campus.manage.biz.exception.BizException;
import cn.zn.smart.campus.manage.biz.exception.ErrorEnum;
import cn.zn.smart.campus.manage.biz.service.TeacherService;
import cn.zn.smart.campus.manage.biz.service.UserService;
import cn.zn.smart.campus.manage.biz.util.IdGeneratorUtil;
import cn.zn.smart.campus.manage.biz.util.ObjMapSwapUtil;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.TeacherInfo;
import cn.zn.smart.campus.manage.dao.service.ITeacherInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/15 23:50
 */
@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {

    @Resource
    private ITeacherInfoService iTeacherInfoService;
    @Resource
    private UserService userService;

    @Override
    public TeacherInfo getByTeaId(String teaId) {
        if (StringUtils.isBlank(teaId)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iTeacherInfoService.getOne(
                new QueryWrapper<TeacherInfo>().eq("teacher_id", teaId));
    }

    @Override
    public ResultPage<TeacherInfo> getTeacherListByPage(QueryPage page, TeacherDTO teacherDto) throws IllegalAccessException {
        if (Objects.isNull(page)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        Map<String, Object> map = null;
        if (Objects.nonNull(teacherDto)){
            //条件筛选：根据职称筛选
            TeacherInfo queryTeacher = new TeacherInfo();
            queryTeacher.setTitle(teacherDto.getTitle());
            map = ObjMapSwapUtil.objectToMap(queryTeacher);
        }
        return iTeacherInfoService.getEntityListByPage(page, map);
    }

    @Override
    public boolean updateBatchByTeaId(List<TeacherDTO> teacherDTOList) {
        if (CollectionUtils.isEmpty(teacherDTOList)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iTeacherInfoService.updateBatchByCondition(this.getTeacherInfoList(teacherDTOList),"teacherId");
    }

    @Override
    public boolean save(TeacherDTO teacher) {
        if (Objects.isNull(teacher)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        TeacherInfo teacherInfo = new TeacherInfo();
        BeanUtils.copyProperties(teacher, teacherInfo);
        teacherInfo.setTeacherId(IdGeneratorUtil.getTeacherId(teacher));
        //添加用户信息
        UserDTO userDTO = new UserDTO();
        //密码为后7-1
        userDTO.setPassword( teacherInfo.getIdNumber().substring( teacherInfo.getIdNumber().length()-7,
                teacherInfo.getIdNumber().length()-1));
        userDTO.setRole(UserRoleEnum.TEACHER.getValue());
        userDTO.setUserMapId(teacherInfo.getTeacherId());
        userService.save(userDTO);
        return iTeacherInfoService.save(teacherInfo);
    }

    @Override
    public boolean deleteBatchByTeaId(List<String> teaIdList) {
        if (CollectionUtils.isEmpty(teaIdList)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        userService.deleteByMapIds(teaIdList);
        return iTeacherInfoService.deleteBatchByEntityId(teaIdList,"teacherId");
    }

    /**
     * teacherInfo和teacherDro数组转换
     *
     * @param teacherDTOList
     * @return
     */
    private List<TeacherInfo> getTeacherInfoList(List<TeacherDTO> teacherDTOList) {
        List<TeacherInfo> list = new ArrayList<>();
        for (TeacherDTO t : teacherDTOList) {
            TeacherInfo temp = new TeacherInfo();
            BeanUtils.copyProperties(t, temp);
            list.add(temp);
        }
        return list;
    }
}
