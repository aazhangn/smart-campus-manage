package cn.zn.smart.campus.manage.biz.service.impl;

import cn.zn.smart.campus.manage.biz.dto.ClassDto;
import cn.zn.smart.campus.manage.biz.exception.BizException;
import cn.zn.smart.campus.manage.biz.exception.ErrorEnum;
import cn.zn.smart.campus.manage.biz.service.ClassService;
import cn.zn.smart.campus.manage.biz.util.IdGeneratorUtil;
import cn.zn.smart.campus.manage.biz.util.ObjMapSwapUtil;
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
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/18 16:02
 */
@Service
public class ClassServiceImpl implements ClassService {

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
        return iClassInfoService.list(
                new QueryWrapper<ClassInfo>().eq("admi_teacher_id", teaId));
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
        if (CollectionUtils.isEmpty(claIdList)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iClassInfoService.deleteBatchByEntityId(claIdList,"classId");
    }

    @Override
    public boolean updateBatchByClaId(List<ClassDto> classList) {
        if (CollectionUtils.isEmpty(classList)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iClassInfoService.updateBatchByCondition(this.getClassInfoList(classList), "classId");
    }

    @Override
    public ClassInfo getByClaId(String classId) {
        if (StringUtils.isBlank(classId)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iClassInfoService.getOne(
                new QueryWrapper<ClassInfo>().eq("class_id", classId));
    }

    @Override
    public ResultPage<ClassInfo> getListByPage(QueryPage queryPage, ClassDto classDto) throws IllegalAccessException {
        if (Objects.isNull(queryPage)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        Map<String, Object> map = null;
        if (Objects.nonNull(classDto)){
            //条件筛选：根据年级、班级筛选
            ClassInfo queryClass = new ClassInfo();
            queryClass.setGrade(classDto.getGrade());
            queryClass.setClassNo(classDto.getClassNo());
            map = ObjMapSwapUtil.objectToMap(queryClass);
        }
        return iClassInfoService.getEntityListByPage(queryPage, map);
    }

    private List<ClassInfo> getClassInfoList(List<ClassDto> classDtoList) {
        List<ClassInfo> list = new ArrayList<>();
        for (ClassDto c : classDtoList) {
            ClassInfo temp = new ClassInfo();
            BeanUtils.copyProperties(c, temp);
            list.add(temp);
        }
        return list;
    }
}
