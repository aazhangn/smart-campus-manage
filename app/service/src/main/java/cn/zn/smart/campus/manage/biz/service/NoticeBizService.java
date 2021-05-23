package cn.zn.smart.campus.manage.biz.service;

import cn.zn.smart.campus.manage.biz.dto.NoticeDTO;
import cn.zn.smart.campus.manage.biz.param.NoticeReceivePeopleParam;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.Notice;

import java.util.List;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/23 10:58
 */
public interface NoticeBizService {
    /**
     * 新增作业
     * @param noticeDTO
     * @return
     */
    public boolean save(NoticeDTO noticeDTO);

    /**
     * 根据作业id查询
     * @param noticeId
     * @returnd
     */
    public Notice getByNoticeId(String noticeId);

    /**
     * 根据接收人参数查询
     * @param param
     * @return
     */
    public List<Notice> getListByReceivePeople(NoticeReceivePeopleParam param);

    /**
     * 根据notice_id删除
     * @param noticeId
     * @return
     */
    public boolean deleteByNoticeId(String noticeId);

    /**
     * 根据notice_id更新
     * @param noticeDTO
     * @return
     */
    public boolean updateByNoticeId(NoticeDTO noticeDTO);


    /**
     * 根据class_id批量删除
     * @param noticeIdList
     * @return
     */
    public boolean deleteBatchByNoticeId(List<String> noticeIdList);

    /**
     * 批量更新
     * @param noticeDTOList
     * @return
     */
    public boolean updateBatchByNoticeId(List<NoticeDTO> noticeDTOList);


    /**
     * 条件分页查询
     * @param queryPage
     * @param noticeDTO
     * @return
     * @throws IllegalAccessException
     */
    public ResultPage<Notice> getListByPage(QueryPage queryPage, NoticeDTO noticeDTO)
            throws IllegalAccessException;
}
