package cn.zn.smart.campus.manage.biz.service;

import cn.zn.smart.campus.manage.biz.dto.FacilityDTO;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.Facility;

import java.util.List;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/20 15:16
 */
public interface FacilityBizService {

    /**
     * 新增作业
     * @param facilityDTO
     * @return
     */
    public boolean save(FacilityDTO facilityDTO);

    /**
     * 根据作业id查询
     * @param facilityId
     * @returnd
     */
    public Facility getByFacId(String facilityId);

    /**
     * 获取所有设施信息
     * @return
     */
    public List<Facility> getAll();


    /**
     * 根据class_id批量删除
     * @param facilityIdList
     * @return
     */
    public boolean deleteBatchByFacId(List<String> facilityIdList);

    /**
     * 批量更新
     * @param facilityList
     * @return
     */
    public boolean updateBatchByFacId(List<FacilityDTO> facilityList);

    /**
     * 条件分页查询
     * @param queryPage
     * @param facilityDTO
     * @return
     * @throws IllegalAccessException
     */
    public ResultPage<Facility> getListByPage(QueryPage queryPage, FacilityDTO facilityDTO)
            throws IllegalAccessException;

    /**
     * 设施借用
     * @param facilityId
     * @param studentId
     * @return
     */
    public boolean borrowFacility(String facilityId,String studentId);


    /**
     * 设施归还
     * @param facilityId
     * @return
     */
    public boolean returnFacility(String facilityId);


}
