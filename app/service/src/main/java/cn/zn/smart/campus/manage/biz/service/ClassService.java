package cn.zn.smart.campus.manage.biz.service;

import cn.zn.smart.campus.manage.biz.dto.ClassDto;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.ClassInfo;

import java.util.List;

/**
 * @Description: 班级biz类
 * @Author: zhangnan
 * @Date: 2021/05/18 16:02
 */
public interface ClassService {
    /**
     * 根据teaId获取所管理班级信息
     * @param classId
     * @return
     */
    public List<ClassInfo> getClassListByTeaId(String classId);

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
    public boolean updateBatchByClaId(List<ClassDto> classList);

    /**
     * 根据classId获取详情
     * @param classId
     * @return
     */
    public ClassInfo getByClaId(String classId);

    /**
     * 条件分页查询
     * @param queryPage
     * @param classDto
     * @return
     * @throws IllegalAccessException
     */
    public ResultPage<ClassInfo> getListByPage(QueryPage queryPage,ClassDto classDto) throws IllegalAccessException;

}
