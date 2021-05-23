package cn.zn.smart.campus.manage.web.controller;

import cn.zn.smart.campus.manage.biz.dto.TeacherDTO;
import cn.zn.smart.campus.manage.biz.exception.BizException;
import cn.zn.smart.campus.manage.biz.exception.ErrorEnum;
import cn.zn.smart.campus.manage.biz.service.TeacherService;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.TeacherInfo;
import cn.zn.smart.campus.manage.web.param.PageParam;
import cn.zn.smart.campus.manage.web.result.Result;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.*;
import com.google.common.collect.Lists;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/20 22:42
 */
@RestController
@RequestMapping("api/teacher")
public class TeacherController {
    @Resource
    private TeacherService teacherService;
    @GetMapping("/get/info")
    public Result<TeacherInfo> getOne(@RequestParam("teacherId") String teacherId){
        try {
            return Result.succeed(teacherService.getByTeaId(teacherId));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @PostMapping("/page/list")
    public Result<ResultPage<TeacherInfo>> listByPage(@RequestBody PageParam<TeacherDTO> param){
        try {
            return Result.succeed(teacherService.getTeacherListByPage(param.getPage(),param.getQueryCon()));
        } catch (IllegalAccessException e) {
            return Result.fail(ErrorEnum.SYS_EXCEPTION.getCode(),e.getMessage());
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody TeacherDTO teacherDTO){
        try {
            return Result.succeed(teacherService.save(teacherDTO));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody TeacherDTO teacherDTO){
        try {
            return Result.succeed(teacherService.updateBatchByTeaId(Lists.newArrayList(teacherDTO)));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @PostMapping("/batch/delete")
    public Result<Boolean> delete(@RequestBody List<String> teacherIds){
        System.out.println(JSON.toJSONString(teacherIds));
        try {
            boolean flag = teacherService.deleteBatchByTeaId(teacherIds);
            if (flag){
                return Result.succeed(true);
            }
            return Result.fail();
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }
}
