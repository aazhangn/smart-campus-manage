package cn.zn.smart.campus.manage.biz.service.impl;

import cn.zn.smart.campus.manage.biz.dto.StudentDTO;
import cn.zn.smart.campus.manage.biz.dto.UserDTO;
import cn.zn.smart.campus.manage.biz.enums.user.UserRoleEnum;
import cn.zn.smart.campus.manage.biz.exception.BizException;
import cn.zn.smart.campus.manage.biz.exception.ErrorEnum;
import cn.zn.smart.campus.manage.biz.service.StudentBizService;
import cn.zn.smart.campus.manage.biz.service.UserService;
import cn.zn.smart.campus.manage.biz.util.IdGeneratorUtil;
import cn.zn.smart.campus.manage.biz.util.ObjMapSwapUtil;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.ClassInfo;
import cn.zn.smart.campus.manage.dao.po.Student;
import cn.zn.smart.campus.manage.dao.service.IClassInfoService;
import cn.zn.smart.campus.manage.dao.service.IStudentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
    @Resource
    private UserService userService;
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

        //添加用户信息
        UserDTO userDTO = new UserDTO();
        //密码为后7-1
        userDTO.setPassword( student.getIdNumber().substring( student.getIdNumber().length()-7,
                student.getIdNumber().length()-1));
        userDTO.setRole(UserRoleEnum.STUDENT.getValue());
        userDTO.setUserMapId(student.getStudentId());
        userService.save(userDTO);
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
        userService.deleteByMapIds(studentIdList);
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

    @Override
    public List<String> getStuIdList() {
        List<Student> studentList  = iStudentService.list();
        if (CollectionUtils.isEmpty(studentList)){
            return null;
        }
        return studentList.stream().map(Student::getStudentId).collect(Collectors.toList());
    }

    @Override
    public List<Student> getListByClassId(String classId) {
        if (StringUtils.isBlank(classId)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        ClassInfo classInfo = iClassInfoService.getByEntityId(classId,"classId");
        if (Objects.isNull(classInfo)) {
            throw new BizException(ErrorEnum.SYS_QUERY_DATA_IS_NULL.getCode(),"该班级不存在");
        }
        return iStudentService.list(new QueryWrapper<Student>().eq("class_id",classId));
    }

    @Override
    public boolean resetClass(String studentId) {
        if (StringUtils.isBlank(studentId)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iStudentService.update(new UpdateWrapper<Student>().eq("student_id",studentId)
                .set("class_id",null));
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
