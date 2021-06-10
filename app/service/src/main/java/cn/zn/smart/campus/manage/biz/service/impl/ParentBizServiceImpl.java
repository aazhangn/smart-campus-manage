package cn.zn.smart.campus.manage.biz.service.impl;

import cn.zn.smart.campus.manage.biz.dto.ParentDto;
import cn.zn.smart.campus.manage.biz.dto.StuParentRelDTO;
import cn.zn.smart.campus.manage.biz.dto.UserDTO;
import cn.zn.smart.campus.manage.biz.enums.user.UserRoleEnum;
import cn.zn.smart.campus.manage.biz.exception.BizException;
import cn.zn.smart.campus.manage.biz.exception.ErrorEnum;
import cn.zn.smart.campus.manage.biz.service.ParentBizService;
import cn.zn.smart.campus.manage.biz.service.UserService;
import cn.zn.smart.campus.manage.biz.util.IdGeneratorUtil;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.Parent;
import cn.zn.smart.campus.manage.dao.po.StuParentRel;
import cn.zn.smart.campus.manage.dao.service.IParentService;
import cn.zn.smart.campus.manage.dao.service.IStuParentRelService;
import cn.zn.smart.campus.manage.dao.service.IStudentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/21 17:15
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ParentBizServiceImpl implements ParentBizService {
    @Resource
    private IParentService iParentService;
    @Resource
    private IStuParentRelService iStuParentRelService;
    @Resource
    private IStudentService iStudentService;
    @Resource
    private UserService userService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(ParentDto parentDto) {
        if (Objects.isNull(parentDto) || CollectionUtils.isEmpty(parentDto.getRels())) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        Parent parent = new Parent();
        BeanUtils.copyProperties(parentDto, parent);
        String parentId = "par_" + IdGeneratorUtil.getUUID();
        parent.setParentId(parentId);

        List<StuParentRel> rels = new ArrayList<>();
        //设置id
        for (StuParentRelDTO rel : parentDto.getRels()) {
            if (Objects.isNull(iStudentService.getByEntityId(rel.getStudentId(), "studentId"))) {
                log.error("添加学生家长关系失败:学号-{},家长id-{},msg-{}", parentDto.getParentId(), rel.getStudentId(),
                        ErrorEnum.SYS_QUERY_DATA_IS_NULL.getMsg());
                //数组中移除
                parentDto.getRels().remove(rel);
                continue;
            }
            //关系初始化
            StuParentRel temp = new StuParentRel();
            temp.setStudentId(rel.getStudentId());
            temp.setRelationshipType(rel.getRelationshipType());
            temp.setParentId(parentId);
            temp.setStuParRelId(rel.getStudentId() + "_" + parent.getParentId());
            rels.add(temp);
        }
        //添加用户信息
        UserDTO userDTO = new UserDTO();
        //密码为后6位
        userDTO.setPassword( parent.getPhoneNumber().substring(parent.getPhoneNumber().length()-6));
        userDTO.setRole(UserRoleEnum.PARENT.getValue());
        userDTO.setUserMapId(parent.getParentId());
        userDTO.setUsername(parent.getPhoneNumber());
        userDTO.setUserId("user_"+parent.getPhoneNumber());
        userService.save(userDTO);

        boolean flag = iParentService.save(parent);
        boolean flag2 = iStuParentRelService.saveBatch(rels);
        return flag && flag2;
    }

    @Override
    public boolean deleteBatch(List<String> parentIdList) {
        if (CollectionUtils.isEmpty(parentIdList)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        userService.deleteByMapIds(parentIdList);
        boolean flag = iParentService.deleteBatchByEntityId(parentIdList, "parentId");
        boolean flag2 = iStuParentRelService.deleteBatchByEntityId(parentIdList, "parentId");
        return flag && flag2;
    }

    @Override
    public boolean updateBatchByParId(List<ParentDto> parentDtoList) {
        if (CollectionUtils.isEmpty(parentDtoList)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        List<Parent> parents = new ArrayList<>();
        List<StuParentRel> rels = new ArrayList<>();
        this.parentDtoListHandler(parentDtoList, parents, rels);
        boolean flag = iParentService.updateBatchByCondition(parents, "parentId");
        boolean flag2 = iStuParentRelService.updateBatchByCondition(rels, "stuParRelId");
        return flag && flag2;
    }

    @Override
    public ParentDto getByParId(String parentId) {
        if (StringUtils.isBlank(parentId)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        ParentDto parentDto = new ParentDto();
        Parent parent = iParentService.getOne(new QueryWrapper<Parent>().eq("parent_id", parentId));
        //家长存在时，查询关系
        if (Objects.nonNull(parent)) {
            BeanUtils.copyProperties(parent, parentDto);
            List<StuParentRel> rels = iStuParentRelService.list(new QueryWrapper<StuParentRel>()
                    .eq("parent_id", parentId));
            parentDto.setRels(this.getRelDtoList(rels));
            return parentDto;
        } else {
            return null;
        }
    }

    @Override
    public ResultPage<ParentDto> getListByPage(QueryPage queryPage, ParentDto parentDto) throws IllegalAccessException {
        if (Objects.isNull(queryPage)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        List<ParentDto> parentDtoList = new ArrayList<>();
        ResultPage<Parent> resultPage = iParentService.getEntityListByPage(queryPage, null);
        //查询对应的关系数组
        for (Parent p : resultPage.getRecords()) {
            ParentDto temp = new ParentDto();
            BeanUtils.copyProperties(p, temp);
            List<StuParentRel> rels = iStuParentRelService.list(new QueryWrapper<StuParentRel>()
                    .eq("parent_id", p.getParentId()));
            temp.setRels(this.getRelDtoList(rels));
            parentDtoList.add(temp);
        }
        //设置结果页
        ResultPage<ParentDto> page = new ResultPage<>();
        BeanUtils.copyProperties(resultPage, page);
        page.setRecords(parentDtoList);
        return page;
    }

    @Override
    public List<String> getStuIds(String parentId) {
        if (StringUtils.isBlank(parentId)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        List<StuParentRel> rels = iStuParentRelService.list(new QueryWrapper<StuParentRel>().eq("parent_id",parentId));
        List<String> stuIds = rels.stream().map(StuParentRel::getStudentId).collect(Collectors.toList());
        return stuIds;
    }

    private void parentDtoListHandler(List<ParentDto> parentDtoList, List<Parent> parents, List<StuParentRel> rels) {
        for (ParentDto parentDto : parentDtoList) {
            Parent parent = new Parent();
            BeanUtils.copyProperties(parentDto, parent);
            parents.add(parent);
            if (Objects.nonNull(parentDto.getRels())) {
                for (StuParentRelDTO s:parentDto.getRels()){
                    StuParentRel rel = new StuParentRel();
                    BeanUtils.copyProperties(s,rel);
                    rel.setParentId(parentDto.getParentId());
                    rel.setStuParRelId(s.getStudentId()+"_"+parentDto.getParentId());
                    rels.add(rel);
                }
            }
        }
    }

    private List<StuParentRelDTO> getRelDtoList(List<StuParentRel> relList) {
        List<StuParentRelDTO> list = new ArrayList<>();
        for (StuParentRel t : relList) {
            StuParentRelDTO temp = new StuParentRelDTO();
            BeanUtils.copyProperties(t, temp);
            list.add(temp);
        }
        return list;
    }
}
