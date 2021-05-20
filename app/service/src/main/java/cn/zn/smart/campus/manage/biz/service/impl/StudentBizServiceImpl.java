package cn.zn.smart.campus.manage.biz.service.impl;

import cn.zn.smart.campus.manage.biz.dto.StudentDTO;
import cn.zn.smart.campus.manage.biz.exception.BizException;
import cn.zn.smart.campus.manage.biz.exception.ErrorEnum;
import cn.zn.smart.campus.manage.biz.service.StudentBizService;
import cn.zn.smart.campus.manage.biz.util.IdGeneratorUtil;
import cn.zn.smart.campus.manage.biz.util.ObjMapSwapUtil;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.ClassInfo;
import cn.zn.smart.campus.manage.dao.po.Student;
import cn.zn.smart.campus.manage.dao.service.IClassInfoService;
import cn.zn.smart.campus.manage.dao.service.IStudentService;
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
 * @Date: 2021/05/20 17:51
 */
@Service
public class StudentBizServiceImpl implements StudentBizService {
    @Resource
    private IClassInfoService iClassInfoService;
    @Resource
    private IStudentService iStudentService;
    @Override
    public boolean save(StudentDTO studentDTO) {
        if (Objects.isNull(studentDTO)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        if (StringUtils.isBlank(studentDTO.getClassId())){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR.getCode(),"classId参数缺失");
        }
        ClassInfo classInfo = iClassInfoService.getByEntityId(studentDTO.getClassId(),"classId");
        if (Objects.isNull(classInfo)){
            throw new BizException(ErrorEnum.SYS_QUERY_DATA_IS_NULL.getCode(),"班级不存在");
        }
        Student student = new Student();
        BeanUtils.copyProperties(studentDTO, student);
        student.setStudentId(IdGeneratorUtil.getStudentId(studentDTO));
        return iStudentService.save(student);
    }

    @Override
    public Student getByStuId(String studentId) {
        if (StringUtils.isBlank(studentId)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iStudentService.getOne(
                new QueryWrapper<Student>().eq("student_id", studentId));

    }

    @Override
    public boolean deleteBatchByStuId(List<String> studentIdList) {
        if (CollectionUtils.isEmpty(studentIdList)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iStudentService.deleteBatchByEntityId(studentIdList,"studentId");
    }

    @Override
    public boolean updateBatchByStuId(List<StudentDTO> studentList) {
        if (CollectionUtils.isEmpty(studentList)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iStudentService.updateBatchByCondition(this.getStudentList(studentList), "studentId");
    }

    @Override
    public ResultPage<Student> getListByPage(QueryPage queryPage, StudentDTO studentDTO) throws IllegalAccessException {
        if (Objects.isNull(queryPage)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        Map<String, Object> map = null;
        if (Objects.nonNull(studentDTO)){
            //条件筛选：根据班级id筛选
            Student query = new Student();
            query.setClassId(studentDTO.getClassId());
            map = ObjMapSwapUtil.objectToMap(query);
        }
        return iStudentService.getEntityListByPage(queryPage, map);
    }

    private List<Student> getStudentList(List<StudentDTO> studentDTOList) {
        List<Student> list = new ArrayList<>();
        for (StudentDTO  s: studentDTOList) {
            Student temp = new Student();
            BeanUtils.copyProperties(s, temp);
            list.add(temp);
        }
        return list;
    }
}
