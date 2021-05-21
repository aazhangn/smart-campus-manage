package cn.zn.smart.campus.manage.web.controller;

import cn.zn.smart.campus.manage.biz.dto.TeacherDTO;
import cn.zn.smart.campus.manage.biz.exception.BizException;
import cn.zn.smart.campus.manage.biz.exception.ErrorEnum;
import cn.zn.smart.campus.manage.biz.service.TeacherService;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.TeacherInfo;
import cn.zn.smart.campus.manage.web.result.Result;
import org.springframework.web.bind.annotation.*;
import cn.zn.smart.campus.manage.web.param.PageParam;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/20 22:42
 */
@RestController
@RequestMapping("api/teacher/manage")
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
}
