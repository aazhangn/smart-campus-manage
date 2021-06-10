package cn.zn.smart.campus.manage.biz.service;

import cn.zn.smart.campus.manage.biz.dto.AnswerDto;
import cn.zn.smart.campus.manage.biz.dto.AssignmentDto;
import cn.zn.smart.campus.manage.biz.param.AssignmentMarkParam;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.Assignment;
import cn.zn.smart.campus.manage.dao.po.StuAssRel;

import java.util.List;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/18 20:56
 */
public interface AssignmentBizService{

    /**
     * 新增作业
     * @param assignmentDto
     * @return
     */
    public boolean save(AssignmentDto assignmentDto);

    /**
     * 根据作业id查询
     * @param assignmentId
     * @returnd
     */
    public Assignment getByAssId(String assignmentId);


    /**
     * 根据class_id批量删除
     * @param assIdList
     * @return
     */
    public boolean deleteBatchByAssId(List<String> assIdList);

    /**
     * 批量更新
     * @param assignmentList
     * @return
     */
    public boolean updateBatchByAssId(List<AssignmentDto> assignmentList);

    /**
     * 条件分页查询
     * @param queryPage
     * @param assignmentDto
     * @return
     * @throws IllegalAccessException
     */
    public ResultPage<Assignment> getListByPage(QueryPage queryPage, AssignmentDto assignmentDto)
            throws IllegalAccessException;

    /**
     * 作业答案提交
     * @param answerDto
     * @return
     */
    public boolean submitAnswer(AnswerDto answerDto);

    /**
     * 作业批改
     * @param param
     * @return
     */
    public boolean markAssignment(AssignmentMarkParam param);

    /**
     * 根据老师id查询作业
     * @param teacherId
     * @return
     */
    public List<Assignment> getAssListByTeaId(String teacherId);


    /**
     * 查询答题情况
     * @param assignmentId
     * @return
     */
    public List<StuAssRel> getAnswerListByAssId(String assignmentId);

}
