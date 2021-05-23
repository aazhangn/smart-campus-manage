package cn.zn.smart.campus.manage.biz.service;

import cn.zn.smart.campus.manage.biz.dto.SecondHandGoodDTO;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.SecondHandGood;

import java.util.List;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/23 11:08
 */
public interface SecondHandGoodBizService {
    /**
     * 新增作业
     * @param goodDTO
     * @return
     */
    public boolean save(SecondHandGoodDTO goodDTO);

    /**
     * 根据作业id查询
     * @param goodId
     * @returnd
     */
    public SecondHandGood getByGoodId(String goodId);

    /**
     * 根据good_id删除
     * @param goodId
     * @return
     */
    public boolean deleteByGoodId(String goodId);

    /**
     * 根据good_id更新
     * @param goodDTO
     * @return
     */
    public boolean updateByGoodId(SecondHandGoodDTO goodDTO);


    /**
     * 批量删除
     * @param goodIdList
     * @return
     */
    public boolean deleteBatchByGoodId(List<String> goodIdList);

    /**
     * 批量更新
     * @param goodDTOList
     * @return
     */
    public boolean updateBatchByGoodId(List<SecondHandGoodDTO> goodDTOList);


    /**
     * 条件分页查询
     * @param queryPage
     * @param goodDTO
     * @return
     * @throws IllegalAccessException
     */
    public ResultPage<SecondHandGood> getListByPage(QueryPage queryPage, SecondHandGoodDTO goodDTO)
            throws IllegalAccessException;
}
