package cn.zn.smart.campus.manage.biz.service.impl;

import cn.zn.smart.campus.manage.biz.dto.FacilityBorrowRecordDTO;
import cn.zn.smart.campus.manage.biz.dto.FacilityDTO;
import cn.zn.smart.campus.manage.biz.enums.facility.FacilityStatusEnum;
import cn.zn.smart.campus.manage.biz.exception.BizException;
import cn.zn.smart.campus.manage.biz.exception.ErrorEnum;
import cn.zn.smart.campus.manage.biz.service.FacilityBizService;
import cn.zn.smart.campus.manage.biz.util.IdGeneratorUtil;
import cn.zn.smart.campus.manage.biz.util.ObjMapSwapUtil;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.Facility;
import cn.zn.smart.campus.manage.dao.po.FacilityBorrowRecord;
import cn.zn.smart.campus.manage.dao.po.Student;
import cn.zn.smart.campus.manage.dao.po.TeacherInfo;
import cn.zn.smart.campus.manage.dao.service.IFacilityBorrowRecordService;
import cn.zn.smart.campus.manage.dao.service.IFacilityService;
import cn.zn.smart.campus.manage.dao.service.IStudentService;
import cn.zn.smart.campus.manage.dao.service.ITeacherInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/20 15:22
 */
@Service
@Slf4j
public class FacilityBizServiceImpl implements FacilityBizService {
    @Resource
    private IFacilityService iFacilityService;
    @Resource
    private ITeacherInfoService iTeacherInfoService;
    @Resource
    private IStudentService iStudentService;
    @Resource
    private IFacilityBorrowRecordService iFacilityBorrowRecordService;
    @Override
    public boolean save(FacilityDTO facilityDTO) {
        if (Objects.isNull(facilityDTO)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        if (StringUtils.isNotBlank(facilityDTO.getAdministratorId())){
            TeacherInfo teacherInfo = iTeacherInfoService.getByEntityId(facilityDTO.getAdministratorId(),
                    "teacherId");
            if (Objects.isNull(teacherInfo)){
                throw new BizException(ErrorEnum.SYS_QUERY_DATA_IS_NULL.getCode(),"该管理员不存在");
            }
        }
        Facility facility = new Facility();
        BeanUtils.copyProperties(facilityDTO, facility);
        facility.setStatus(FacilityStatusEnum.FREE.getStatus());
        facility.setFacilityId("fac_" + IdGeneratorUtil.getUUID());
        return iFacilityService.save(facility);
    }

    @Override
    public Facility getByFacId(String facilityId) {
        if (StringUtils.isBlank(facilityId)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iFacilityService.getOne(
                new QueryWrapper<Facility>().eq("facility_id", facilityId));
    }

    @Override
    public List<Facility> getAll() {
        return iFacilityService.list();
    }

    @Override
    public boolean deleteBatchByFacId(List<String> facilityIdList) {
        if (CollectionUtils.isEmpty(facilityIdList)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        //若正在使用，禁止删除
        for (String facilityId: facilityIdList) {
            QueryWrapper<Facility> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("facility_id",facilityId)
                    .eq("status", FacilityStatusEnum.BUSY.getStatus());
            if (Objects.nonNull(iFacilityService.getOne(queryWrapper))){
                log.info("删除失败：设施"+facilityId+"正在使用，禁止删除");
                facilityIdList.remove(facilityId);
            }
        }
        return iFacilityService.deleteBatchByEntityId(facilityIdList,"facilityId");
    }

    @Override
    public boolean updateBatchByFacId(List<FacilityDTO> facilityList) {
        if (CollectionUtils.isEmpty(facilityList)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        return iFacilityService.updateBatchByCondition(this.getFacilityList(facilityList), "facilityId");
    }

    @Override
    public ResultPage<Facility> getListByPage(QueryPage queryPage, FacilityDTO facilityDTO) throws IllegalAccessException {
        if (Objects.isNull(queryPage)) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        Map<String, Object> map = null;
        if (Objects.nonNull(facilityDTO)){
            //条件筛选：根据类型筛选
            Facility query = new Facility();
            query.setType(facilityDTO.getType());
            map = ObjMapSwapUtil.objectToMap(query);
        }
        return iFacilityService.getEntityListByPage(queryPage, map);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean borrowFacility(String facilityId,String studentId) {
        if (StringUtils.isBlank(facilityId)||StringUtils.isBlank(studentId)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        Facility facility = iFacilityService.getByEntityId(facilityId, "facilityId");
        Student student = iStudentService.getByEntityId(studentId,"studentId");
        if (Objects.isNull(facility)||Objects.isNull(student)){
            throw new BizException(ErrorEnum.SYS_QUERY_DATA_IS_NULL.getCode(),"设施或借用人不存在");
        }
        if (!StringUtils.equals(facility.getStatus(),FacilityStatusEnum.FREE.getStatus())){
            throw new BizException(ErrorEnum.SYS_ILLEGAL_OPERATION.getCode(),"设施使用中");
        }
        UpdateWrapper<Facility> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("facility_id",facilityId).set("status",FacilityStatusEnum.BUSY.getStatus());
        boolean f1 = iFacilityService.update(updateWrapper);

        //借用时间数据库自动设置
        FacilityBorrowRecord record = new FacilityBorrowRecord();
        record.setBorrowRecordId(IdGeneratorUtil.getUUID());
        record.setFacilityId(facilityId);
        record.setStudentId(studentId);
        boolean f2 = iFacilityBorrowRecordService.save(record);
        return f1 && f2;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean returnFacility(String facilityId) {
        if (StringUtils.isBlank(facilityId)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        Facility facility = iFacilityService.getByEntityId(facilityId, "facilityId");
        QueryWrapper<FacilityBorrowRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("facility_id",facilityId);
        FacilityBorrowRecord record = iFacilityBorrowRecordService.getOne(wrapper);
        if (Objects.isNull(facility)){
            throw new BizException(ErrorEnum.SYS_QUERY_DATA_IS_NULL.getCode(),"设施不存在");
        }
        if (StringUtils.equals(facility.getStatus(),FacilityStatusEnum.FREE.getStatus())||Objects.isNull(record)){
            throw new BizException(ErrorEnum.SYS_ILLEGAL_OPERATION.getCode(),"设施未被借用");
        }
        UpdateWrapper<Facility> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("facility_id",facilityId).set("status",FacilityStatusEnum.FREE.getStatus());
        boolean f1 = iFacilityService.update(updateWrapper);

        UpdateWrapper<FacilityBorrowRecord> updateWrapper1 = new UpdateWrapper<>();
        updateWrapper1.eq("borrow_record_id",record.getBorrowRecordId()).set("return_time",new Date());
        boolean f2 = iFacilityBorrowRecordService.update(updateWrapper1);
        return f1 && f2;
    }

    @Override
    public List<FacilityBorrowRecordDTO> getByBorrowStudentId(String studentId) {
        if (StringUtils.isBlank(studentId)){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        //查询未归还的借用记录
        List<FacilityBorrowRecord> records = iFacilityBorrowRecordService.list(new QueryWrapper<FacilityBorrowRecord>()
                .eq("student_id",studentId).isNull("return_time"));
        List<FacilityBorrowRecordDTO> returnRecords = new ArrayList<>();
        for (FacilityBorrowRecord r:records){
            Facility facility = iFacilityService.getByEntityId(r.getFacilityId(),"facilityId");
            if (Objects.isNull(facility)){
                throw new BizException(ErrorEnum.SYS_QUERY_DATA_IS_NULL.getCode(),"设施不存在");
            }
            //核对使用状态
            if (facility.getStatus().equals(FacilityStatusEnum.BUSY.getStatus())){
                FacilityBorrowRecordDTO returnRecord = new FacilityBorrowRecordDTO();
                BeanUtils.copyProperties(r,returnRecord);
                BeanUtils.copyProperties(facility,returnRecord);
                returnRecords.add(returnRecord);
            }
        }
        return returnRecords;
    }

    private List<Facility> getFacilityList(List<FacilityDTO> facilityDtoList) {
        List<Facility> list = new ArrayList<>();
        for (FacilityDTO f : facilityDtoList) {
            Facility temp = new Facility();
            BeanUtils.copyProperties(f, temp);
            list.add(temp);
        }
        return list;
    }
}
