package cn.zn.smart.campus.manage.biz.service.impl;

import cn.zn.smart.campus.manage.biz.dto.ClassDto;
import cn.zn.smart.campus.manage.biz.dto.TeacherDto;
import cn.zn.smart.campus.manage.biz.exception.BizException;
import cn.zn.smart.campus.manage.biz.exception.ErrorEnum;
import cn.zn.smart.campus.manage.biz.service.ClassInfoService;
import cn.zn.smart.campus.manage.biz.util.IdGeneratorUtil;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.ClassInfo;
import cn.zn.smart.campus.manage.dao.po.TeacherInfo;
import cn.zn.smart.campus.manage.dao.service.IClassInfoService;
import cn.zn.smart.campus.manage.dao.service.ITeacherInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/18 16:02
 */
@Service
public class ClassInfoServiceImpl implements ClassInfoService {

    @Resource
    private IClassInfoService iClassInfoService;
    @Resource
    private ITeacherInfoService iTeacherInfoService;

    @Override
    public List<ClassInfo> getClassListByTeaId(String teaId) {
        if (StringUtils.isBlank(teaId)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        TeacherInfo teacherInfo = iTeacherInfoService.getOne(
                new QueryWrapper<TeacherInfo>().eq("teacher_id", teaId));
        if (Objects.isNull(teacherInfo)) {
            throw new BizException(ErrorEnum.SYS_QUERY_DATA_IS_NULL);
        }
        List<ClassInfo> classList = iClassInfoService.list(
                new QueryWrapper<ClassInfo>().eq("admi_teacher_id", teaId));
        return classList;
    }

    @Override
    public boolean save(ClassDto classDto) {
        if (Objects.isNull(classDto)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        ClassInfo classInfo = new ClassInfo();
        BeanUtils.copyProperties(classDto, classInfo);
        classInfo.setClassId(IdGeneratorUtil.getClassId(classDto));
        if (StringUtils.isBlank(classDto.getName())){
            classInfo.setName("class-"+classDto.getGrade()+"-"+classDto.getClassNo());
        }
        return iClassInfoService.save(classInfo);
    }

    @Override
    public boolean deleteBatchByClaId(List<String> claIdList) {
        return false;
    }

    @Override
    public boolean updateBatchByClaId(List<String> classList) {
        return false;
    }

    @Override
    public boolean getByClaId(String classId) {
        return false;
    }

    @Override
    public ResultPage<TeacherInfo> getListByPage(QueryPage queryPage, TeacherDto teacherDto) throws IllegalAccessException {
        return null;
    }
}
