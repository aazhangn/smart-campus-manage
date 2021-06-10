package cn.zn.smart.campus.manage.biz.service.impl;

import cn.zn.smart.campus.manage.biz.dto.AnswerDto;
import cn.zn.smart.campus.manage.biz.dto.AssignmentDto;
import cn.zn.smart.campus.manage.biz.enums.assignment.AnswerStatusEnum;
import cn.zn.smart.campus.manage.biz.enums.assignment.AssignmentIsObjectiveEnum;
import cn.zn.smart.campus.manage.biz.enums.assignment.MesteryStatusEnum;
import cn.zn.smart.campus.manage.biz.exception.BizException;
import cn.zn.smart.campus.manage.biz.exception.ErrorEnum;
import cn.zn.smart.campus.manage.biz.param.AssignmentMarkParam;
import cn.zn.smart.campus.manage.biz.service.AssignmentBizService;
import cn.zn.smart.campus.manage.biz.util.IdGeneratorUtil;
import cn.zn.smart.campus.manage.biz.util.ObjMapSwapUtil;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.Assignment;
import cn.zn.smart.campus.manage.dao.po.StuAssRel;
import cn.zn.smart.campus.manage.dao.po.Student;
import cn.zn.smart.campus.manage.dao.po.WrongTopic;
import cn.zn.smart.campus.manage.dao.service.IAssignmentService;
import cn.zn.smart.campus.manage.dao.service.IStuAssRelService;
import cn.zn.smart.campus.manage.dao.service.IStudentService;
import cn.zn.smart.campus.manage.dao.service.IWrongTopicService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/18 20:56
 */
@Service
@Slf4j
public class AssignmentBizServiceImpl implements AssignmentBizService {
    @Resource
    private IAssignmentService iAssignmentService;
    @Resource
    private IStuAssRelService iStuAssRelService;
    @Resource
    private IStudentService iStudentService;
    @Resource
    private IWrongTopicService iWrongTopicService;
    @Override
    public boolean save(AssignmentDto assignmentDto) {
        if (Objects.isNull(assignmentDto)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        Assignment assignment = new Assignment();
        BeanUtils.copyProperties(assignmentDto, assignment);
        assignment.setAssignmentId("ass_" + IdGeneratorUtil.getUUID());
        return iAssignmentService.save(assignment);
    }

    @Override
    public Assignment getByAssId(String assignmentId) {
        if (StringUtils.isBlank(assignmentId)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iAssignmentService.getOne(
                new QueryWrapper<Assignment>().eq("assignment_id", assignmentId));
    }

    @Override
    public boolean deleteBatchByAssId(List<String> assIdList) {
        if (CollectionUtils.isEmpty(assIdList)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        //若已有答题记录，禁止删除
        for (String assId: assIdList) {
            QueryWrapper<StuAssRel> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("assignment_id",assId);
            if (!CollectionUtils.isEmpty(iStuAssRelService.list(queryWrapper))){
                log.info("删除失败：作业"+assId+"已有作答记录，禁止删除");
                assIdList.remove(assId);
            }
        }
        return iAssignmentService.deleteBatchByEntityId(assIdList,"assignmentId");
    }

    @Override
    public boolean updateBatchByAssId(List<AssignmentDto> assignmentList) {
        if (CollectionUtils.isEmpty(assignmentList)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iAssignmentService.updateBatchByCondition(this.getAssignmentList(assignmentList), "assignmentId");
    }

    @Override
    public ResultPage<Assignment> getListByPage(QueryPage queryPage, AssignmentDto assignmentDto) throws IllegalAccessException {
        if (Objects.isNull(queryPage)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        Map<String, Object> map = null;
        if (Objects.nonNull(assignmentDto)){
            //条件筛选：根据类型、科目、教师id筛选
            Assignment queryAss = new Assignment();
            queryAss.setType(assignmentDto.getType());
            queryAss.setSubject(assignmentDto.getSubject());
            queryAss.setTeacherId(assignmentDto.getTeacherId());
            map = ObjMapSwapUtil.objectToMap(queryAss);
        }
        return iAssignmentService.getEntityListByPage(queryPage, map);
    }

    @Override
    public boolean submitAnswer(AnswerDto answerDto) {
        if (Objects.isNull(answerDto)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        Assignment assignment = iAssignmentService.getOne(new QueryWrapper<Assignment>()
                .eq("assignment_id",answerDto.getAssignmentId()));
        Student student  = iStudentService.getOne(new QueryWrapper<Student>()
                .eq("student_id",answerDto.getStudentId()));
        if (Objects.isNull(assignment)||Objects.isNull(student)){
            throw new BizException(ErrorEnum.SYS_QUERY_DATA_IS_NULL.getCode(),"作业或学生不存在");
        }
        StuAssRel temp = iStuAssRelService.getByEntityId(
                answerDto.getStudentId()+"_"+answerDto.getAssignmentId(),"stuAssRelId");
        if (Objects.nonNull(temp)){
            throw new BizException(ErrorEnum.SYS_ILLEGAL_OPERATION.getCode(),"该题目该同学已作答");
        }
        StuAssRel stuAssRel = new StuAssRel();
        BeanUtils.copyProperties(answerDto,stuAssRel);
        stuAssRel.setStuAssRelId(answerDto.getStudentId()+"_"+answerDto.getAssignmentId());
        //客观题，自动给分
        if (assignment.getIsObjective().equals(AssignmentIsObjectiveEnum.OBJECTIVE.getValue())){
            stuAssRel.setStatus(AnswerStatusEnum.FINISH_MARK.getValue());
            this.answerHandler(answerDto,assignment,stuAssRel);
        }else{
            stuAssRel.setStatus(AnswerStatusEnum.WAITING_MARK.getValue());
        }
        return iStuAssRelService.save(stuAssRel);
    }

    @Override
    public boolean markAssignment(AssignmentMarkParam param) {
        if (Objects.isNull(param)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        StuAssRel stuAssRel = iStuAssRelService.getByEntityId(
                param.getStuAssRelId(),"stuAssRelId");
        if (Objects.isNull(stuAssRel)){
            throw new BizException(ErrorEnum.SYS_QUERY_DATA_IS_NULL.getCode(),"答题记录不存在");
        }
        //已批阅
        if (Objects.nonNull(stuAssRel.getStatus())&&stuAssRel.getStatus().equals(AnswerStatusEnum.FINISH_MARK.getValue())){
            throw new BizException(ErrorEnum.SYS_ILLEGAL_OPERATION.getCode(),"该条答题记录已批阅");
        }
        StuAssRel temp = new StuAssRel();
        temp.setStuAssRelId(stuAssRel.getStuAssRelId());
        temp.setScore(param.getScore());
        temp.setStatus(AnswerStatusEnum.FINISH_MARK.getValue());
        return iStuAssRelService.update(temp,new UpdateWrapper<StuAssRel>().eq("stu_ass_rel_id",stuAssRel.getStuAssRelId()));
    }

    @Override
    public List<Assignment> getAssListByTeaId(String teacherId) {
        if (StringUtils.isBlank(teacherId)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iAssignmentService.list(new QueryWrapper<Assignment>().eq("teacher_id",teacherId)
                .orderByDesc("create_time"));
    }

    @Override
    public List<StuAssRel> getAnswerListByAssId(String assignmentId) {
        if (StringUtils.isBlank(assignmentId)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iStuAssRelService.list(new QueryWrapper<StuAssRel>().eq("assignment_id",assignmentId));
    }

    private List<Assignment> getAssignmentList(List<AssignmentDto> assignmentDtoList) {
        List<Assignment> list = new ArrayList<>();
        for (AssignmentDto t : assignmentDtoList) {
            Assignment temp = new Assignment();
            BeanUtils.copyProperties(t, temp);
            list.add(temp);
        }
        return list;
    }

    /**
     * 答案处理
     * @param answerDto
     * @param assignment
     * @param stuAssRel
     */
    private void answerHandler(AnswerDto answerDto,Assignment assignment,StuAssRel stuAssRel){
        if (answerDto.getAnswer().equals(assignment.getSuggestedAnswer())){
            stuAssRel.setScore(assignment.getScore());
        }else{
            stuAssRel.setScore(0.0);
            WrongTopic wrongTopic = new WrongTopic();
            wrongTopic.setWrongTopicId("wt_"+IdGeneratorUtil.getUUID());
            wrongTopic.setAssignmentId(assignment.getAssignmentId());
            wrongTopic.setStuAssRelId(stuAssRel.getStuAssRelId());
            wrongTopic.setMesteryStatus(MesteryStatusEnum.NOT_FAMILIAR.getStatus());
            wrongTopic.setType(assignment.getType());
            iWrongTopicService.save(wrongTopic);
        }
    }
}
