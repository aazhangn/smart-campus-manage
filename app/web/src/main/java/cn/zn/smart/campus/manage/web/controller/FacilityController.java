package cn.zn.smart.campus.manage.web.controller;

import cn.zn.smart.campus.manage.biz.dto.FacilityDTO;
import cn.zn.smart.campus.manage.biz.exception.BizException;
import cn.zn.smart.campus.manage.biz.exception.ErrorEnum;
import cn.zn.smart.campus.manage.biz.service.FacilityBizService;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.Facility;
import cn.zn.smart.campus.manage.web.param.PageParam;
import cn.zn.smart.campus.manage.web.result.Result;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/23 13:27
 */
@RestController
@RequestMapping("api/facility")
public class FacilityController {
    @Resource
    private FacilityBizService facilityBizService;

    @GetMapping("/get/info")
    public Result<Facility> getOne(@RequestParam("facilityId")String facilityId){
        try {
            return Result.succeed(facilityBizService.getByFacId(facilityId));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @PostMapping("/page/list")
    public Result<ResultPage<Facility>> listByPage(@RequestBody PageParam<FacilityDTO> param){
        try {
            return Result.succeed(facilityBizService.getListByPage(param.getPage(),param.getQueryCon()));
        } catch (IllegalAccessException e) {
            return Result.fail(ErrorEnum.SYS_EXCEPTION.getCode(),e.getMessage());
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody FacilityDTO facilityDTO){
        try {
            return Result.succeed(facilityBizService.save(facilityDTO));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody FacilityDTO facilityDTO){
        try {
            return Result.succeed(facilityBizService.updateBatchByFacId(Lists.newArrayList(facilityDTO)));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @PostMapping("/batch/delete")
    public Result<Boolean> delete(@RequestBody List<String> facilityIds){
        try {
            return Result.succeed(facilityBizService.deleteBatchByFacId(facilityIds));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }
}