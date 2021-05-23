package cn.zn.smart.campus.manage.biz.service.impl;

import cn.zn.smart.campus.manage.biz.dto.NoticeDTO;
import cn.zn.smart.campus.manage.biz.enums.notice.ReceiveScopeEnum;
import cn.zn.smart.campus.manage.biz.exception.BizException;
import cn.zn.smart.campus.manage.biz.exception.ErrorEnum;
import cn.zn.smart.campus.manage.biz.param.NoticeReceiveParam;
import cn.zn.smart.campus.manage.biz.param.NoticeReceivePeopleParam;
import cn.zn.smart.campus.manage.biz.service.NoticeBizService;
import cn.zn.smart.campus.manage.biz.util.IdGeneratorUtil;
import cn.zn.smart.campus.manage.biz.util.ObjMapSwapUtil;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.Notice;
import cn.zn.smart.campus.manage.dao.service.INoticeService;
import com.alibaba.fastjson.JSON;
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
 * @Date: 2021/05/23 11:22
 */
@Service
public class NoticeBizServiceImpl implements NoticeBizService {

    @Resource
    private INoticeService iNoticeService;
    @Override
    public boolean save(NoticeDTO noticeDTO) {
        if (Objects.isNull(noticeDTO)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        Notice notice = new Notice();
        BeanUtils.copyProperties(noticeDTO, notice);
        notice.setNoticeId("no_" + IdGeneratorUtil.getUUID());
        NoticeReceiveParam param= noticeDTO.getNoticeReceiveParam();
        //若接收参数未设置 默认为all
        if (Objects.isNull(param)|| StringUtils.isBlank(param.getScope())){
            param = new NoticeReceiveParam();
            param.setScope(ReceiveScopeEnum.ALL.getValue());
        }
        notice.setNoticeReceiveParam(JSON.toJSONString(param));
        return iNoticeService.save(notice);
    }

    @Override
    public Notice getByNoticeId(String noticeId) {
        if (StringUtils.isBlank(noticeId)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iNoticeService.getByEntityId(noticeId,"noticeId");
    }

    @Override
    // todo
    public List<Notice> getListByReceivePeople(NoticeReceivePeopleParam param) {
        if (Objects.isNull(param)||StringUtils.isBlank(param.getRole())||StringUtils.isBlank(param.getReceivePeopleId())){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        String role = param.getRole();
//        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
//        if (role.equals(ReceivePeopleRoleEnum.TEACHER.getRole())){
//        }
//        if (role.equals(ReceivePeopleRoleEnum.STUDENT.getRole())){
//
//        }
//        if (role.equals(ReceivePeopleRoleEnum.PARENT.getRole())){
//
//        }

        return null;
    }

    @Override
    public boolean deleteByNoticeId(String noticeId) {
        if (StringUtils.isBlank(noticeId)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iNoticeService.remove(new QueryWrapper<Notice>().eq("notice_id",noticeId));
    }

    @Override
    public boolean updateByNoticeId(NoticeDTO noticeDTO) {
        if (Objects.isNull(noticeDTO)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        Notice notice = new Notice();
        BeanUtils.copyProperties(noticeDTO,notice);
        //参数不为空，转字符串
        if (Objects.nonNull(noticeDTO.getNoticeReceiveParam())){
            notice.setNoticeReceiveParam(JSON.toJSONString(noticeDTO.getNoticeReceiveParam()));
        }
        return iNoticeService.update(notice,new UpdateWrapper<Notice>().eq("notice_id",notice.getNoticeId()));
    }

    @Override
    public boolean deleteBatchByNoticeId(List<String> noticeIdList) {
        if (CollectionUtils.isEmpty(noticeIdList)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
       return iNoticeService.deleteBatchByEntityId(noticeIdList,"noticeId");
    }

    @Override
    public boolean updateBatchByNoticeId(List<NoticeDTO> noticeDTOList) {
        if (CollectionUtils.isEmpty(noticeDTOList)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iNoticeService.updateBatchByCondition(this.getNoticeList(noticeDTOList), "noticeId");
    }

    @Override
    public ResultPage<Notice> getListByPage(QueryPage queryPage, NoticeDTO noticeDTO) throws IllegalAccessException {
        if (Objects.isNull(queryPage)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        Map<String, Object> map = null;
        if (Objects.nonNull(noticeDTO)){
            //条件筛选：根据教师工号筛选
            Notice query = new Notice();
            query.setTeacherId(noticeDTO.getTeacherId());
            map = ObjMapSwapUtil.objectToMap(query);
        }
        return iNoticeService.getEntityListByPage(queryPage, map);
    }

    private List<Notice> getNoticeList(List<NoticeDTO> noticeDTOList) {
        List<Notice> list = new ArrayList<>();
        for (NoticeDTO n : noticeDTOList) {
            Notice temp = new Notice();
            BeanUtils.copyProperties(n, temp);
            list.add(temp);
        }
        return list;
    }
}
