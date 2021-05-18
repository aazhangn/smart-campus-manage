package cn.zn.smart.campus.manage.biz.service;

import cn.zn.smart.campus.manage.biz.dto.ClassDto;
import cn.zn.smart.campus.manage.biz.dto.TeacherDto;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.ClassInfo;
import cn.zn.smart.campus.manage.dao.po.TeacherInfo;

import java.util.List;

/**
 * @Description: 班级biz类
 * @Author: zhangnan
 * @Date: 2021/05/18 16:02
 */
public interface ClassInfoService {
    /**
     * 根据teaId获取所管理班级信息
     * @param teaId
     * @return
     */
    public List<ClassInfo> getClassListByTeaId(String teaId);

    /**
     * 新增班级
     * @param classDto
     * @return
     */
    public boolean save(ClassDto classDto);

    /**
     * 根据class_id批量删除
     * @param claIdList
     * @return
     */
    public boolean deleteBatchByClaId(List<String> claIdList);

    /**
     * 批量更新
     * @param classList
     * @return
     */
    public boolean updateBatchByClaId(List<String> classList);

    /**
     * 根据classId获取详情
     * @param classId
     * @return
     */
    public boolean getByClaId(String classId);

    /**
     * 条件分页查询
     * @param queryPage
     * @param teacherDto
     * @return
     * @throws IllegalAccessException
     */
    public ResultPage<TeacherInfo> getListByPage(QueryPage queryPage, TeacherDto teacherDto) throws IllegalAccessException;

}
