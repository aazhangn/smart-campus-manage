package cn.zn.smart.campus.manage.web.controller;

import cn.zn.smart.campus.manage.biz.dto.ClassDTO;
import cn.zn.smart.campus.manage.biz.exception.BizException;
import cn.zn.smart.campus.manage.biz.exception.ErrorEnum;
import cn.zn.smart.campus.manage.biz.service.ClassService;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.ClassInfo;
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
@RequestMapping("api/class")
public class ClassController {
    @Resource
    private ClassService classService;

    @GetMapping("/get/info")
    public Result<ClassInfo> getOne(@RequestParam("classId")String classId){
        try {
            return Result.succeed(classService.getByClaId(classId));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @GetMapping("/get/classIds")
    public Result<List<String>> getClassIds(){
        try {
            return Result.succeed(classService.getClassIdList());
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }


    @PostMapping("/page/list")
    public Result<ResultPage<ClassInfo>> listByPage(@RequestBody PageParam<ClassDTO> param){
        try {
            return Result.succeed(classService.getListByPage(param.getPage(),param.getQueryCon()));
        } catch (IllegalAccessException e) {
            return Result.fail(ErrorEnum.SYS_EXCEPTION.getCode(),e.getMessage());
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody ClassDTO classDTO){
        try {
            return Result.succeed(classService.save(classDTO));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody ClassDTO classDTO ){
        try {
            return Result.succeed(classService.updateBatchByClaId(Lists.newArrayList(classDTO)));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @PostMapping("/batch/delete")
    public Result<Boolean> delete(@RequestBody List<String> classIdList){
        try {
            return Result.succeed(classService.deleteBatchByClaId(classIdList));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }
}