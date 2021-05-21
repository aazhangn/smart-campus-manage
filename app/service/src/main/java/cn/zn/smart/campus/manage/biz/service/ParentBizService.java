package cn.zn.smart.campus.manage.biz.service;

import cn.zn.smart.campus.manage.biz.dto.ParentDto;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import cn.zn.smart.campus.manage.dao.page.ResultPage;

import java.util.List;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/21 17:05
 */
public interface ParentBizService {

    /**
     * 新增
     * @param parentDto
     * @return
     */
    public boolean save(ParentDto parentDto);

    /**
     * 批量删除
     * @param parentIdList
     * @return
     */
    public boolean deleteBatch(List<String> parentIdList);


    /**
     * 批量更新
     * @param parentDtoList
     * @return
     */
    public boolean updateBatchByParId(List<ParentDto> parentDtoList);

    /**
     * 查询详情
     * @param parentId
     * @return
     */
    public ParentDto getByParId(String parentId);


    /**
     * 分页查询
     * @param queryPage
     * @param parentDto
     * @return
     * @throws IllegalAccessException
     */
    public ResultPage<ParentDto> getListByPage(QueryPage queryPage, ParentDto parentDto)
            throws IllegalAccessException;

    /**
     * 获取孩子ids
     * @param parentId
     * @return
     */
    public List<String> getStuIds(String parentId);

}
