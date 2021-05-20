package cn.zn.smart.campus.manage.biz.service.impl;

import cn.zn.smart.campus.manage.biz.dto.AssignmentDto;
import cn.zn.smart.campus.manage.biz.exception.BizException;
import cn.zn.smart.campus.manage.biz.exception.ErrorEnum;
import cn.zn.smart.campus.manage.biz.service.AssignmentBizService;
import cn.zn.smart.campus.manage.biz.util.IdGeneratorUtil;
import cn.zn.smart.campus.manage.biz.util.ObjMapSwapUtil;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.Assignment;
import cn.zn.smart.campus.manage.dao.po.StuAssRel;
import cn.zn.smart.campus.manage.dao.service.IAssignmentService;
import cn.zn.smart.campus.manage.dao.service.IStuAssRelService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
            //条件筛选：根据类型、科目筛选
            Assignment queryAss = new Assignment();
            queryAss.setType(assignmentDto.getType());
            queryAss.setSubject(assignmentDto.getSubject());
            map = ObjMapSwapUtil.objectToMap(queryAss);
        }
        return iAssignmentService.getEntityListByPage(queryPage, map);
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
}
