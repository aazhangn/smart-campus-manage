package cn.zn.smart.campus.manage.biz.service.impl;

import cn.zn.smart.campus.manage.biz.dto.SecondHandGoodDTO;
import cn.zn.smart.campus.manage.biz.exception.BizException;
import cn.zn.smart.campus.manage.biz.exception.ErrorEnum;
import cn.zn.smart.campus.manage.biz.service.SecondHandGoodBizService;
import cn.zn.smart.campus.manage.biz.util.IdGeneratorUtil;
import cn.zn.smart.campus.manage.biz.util.ObjMapSwapUtil;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.SecondHandGood;
import cn.zn.smart.campus.manage.dao.po.Student;
import cn.zn.smart.campus.manage.dao.service.ISecondHandGoodService;
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

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/23 12:46
 */
@Service
public class SecondHandGoodBizServiceImpl implements SecondHandGoodBizService {
    @Resource
    private ISecondHandGoodService iSecondHandGoodService;
    @Resource
    private IStudentService iStudentService;
    @Override
    public boolean save(SecondHandGoodDTO goodDTO) {
        if (Objects.isNull(goodDTO)||StringUtils.isBlank(goodDTO.getIssueStudentId())) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        Student student = iStudentService.getByEntityId(goodDTO.getIssueStudentId(),"studentId");
        if (Objects.isNull(student)){
            throw  new BizException(ErrorEnum.SYS_QUERY_DATA_IS_NULL.getCode(),"该学生不存在");
        }
        SecondHandGood good = new SecondHandGood();
        BeanUtils.copyProperties(goodDTO, good);
        good.setGoodId("good_"+IdGeneratorUtil.getUUID());
        return iSecondHandGoodService.save(good);
    }

    @Override
    public SecondHandGood getByGoodId(String goodId) {
        if (StringUtils.isBlank(goodId)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iSecondHandGoodService.getOne(
                new QueryWrapper<SecondHandGood>().eq("good_id", goodId));
    }

    @Override
    public boolean deleteByGoodId(String goodId) {
        if (StringUtils.isBlank(goodId)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iSecondHandGoodService.remove(new QueryWrapper<SecondHandGood>().eq("good_id",goodId));
    }

    @Override
    public boolean updateByGoodId(SecondHandGoodDTO goodDTO) {
        if (Objects.isNull(goodDTO)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        //更新发布人
        if (StringUtils.isNotBlank(goodDTO.getIssueStudentId())){
            Student student = iStudentService.getByEntityId(goodDTO.getIssueStudentId(),"studentId");
            if (Objects.isNull(student)){
                throw  new BizException(ErrorEnum.SYS_QUERY_DATA_IS_NULL.getCode(),"该学生不存在");
            }
        }
        SecondHandGood good = new SecondHandGood();
        BeanUtils.copyProperties(goodDTO,good);
        return iSecondHandGoodService.update(good,new UpdateWrapper<SecondHandGood>().eq("good_id",good.getGoodId()));

    }

    @Override
    public boolean deleteBatchByGoodId(List<String> goodIdList) {
        if (CollectionUtils.isEmpty(goodIdList)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iSecondHandGoodService.deleteBatchByEntityId(goodIdList,"goodId");
    }

    @Override
    public boolean updateBatchByGoodId(List<SecondHandGoodDTO> goodDTOList) {
        if (CollectionUtils.isEmpty(goodDTOList)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iSecondHandGoodService.updateBatchByCondition(this.getGoodList(goodDTOList), "goodId");
    }

    @Override
    public ResultPage<SecondHandGood> getListByPage(QueryPage queryPage, SecondHandGoodDTO goodDTO) throws IllegalAccessException {
        if (Objects.isNull(queryPage)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        Map<String, Object> map = null;
        if (Objects.nonNull(goodDTO)){
            //条件筛选：根据类型筛选
            SecondHandGood query = new SecondHandGood();
            query.setType(goodDTO.getType());
            map = ObjMapSwapUtil.objectToMap(query);
        }
        return iSecondHandGoodService.getEntityListByPage(queryPage, map);
    }

    private List<SecondHandGood> getGoodList(List<SecondHandGoodDTO> goodDTOList) {
        List<SecondHandGood> list = new ArrayList<>();
        for (SecondHandGoodDTO c : goodDTOList) {
            SecondHandGood temp = new SecondHandGood();
            BeanUtils.copyProperties(c, temp);
            list.add(temp);
        }
        return list;
    }
}
