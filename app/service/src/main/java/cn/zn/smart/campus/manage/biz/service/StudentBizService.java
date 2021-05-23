package cn.zn.smart.campus.manage.biz.service;

import cn.zn.smart.campus.manage.biz.dto.StudentDTO;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.Student;

import java.util.List;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/20 17:46
 */
public interface StudentBizService {


    /**
     * 新增作业
     * @param studentDTO
     * @return
     */
    public boolean save(StudentDTO studentDTO);

    /**
     * 根据作业id查询
     * @param studentId
     * @returnd
     */
    public Student getByStuId(String studentId);


    /**
     * 根据class_id批量删除
     * @param studentIdList
     * @return
     */
    public boolean deleteBatchByStuId(List<String> studentIdList);

    /**
     * 批量更新
     * @param studentList
     * @return
     */
    public boolean updateBatchByStuId(List<StudentDTO> studentList);

    /**
     * 条件分页查询
     * @param queryPage
     * @param studentDTO
     * @return
     * @throws IllegalAccessException
     */
    public ResultPage<Student> getListByPage(QueryPage queryPage, StudentDTO studentDTO)
            throws IllegalAccessException;

    /**
     * 获取学生学号列表
     * @return
     */
    public List<String> getStuIdList();
}
