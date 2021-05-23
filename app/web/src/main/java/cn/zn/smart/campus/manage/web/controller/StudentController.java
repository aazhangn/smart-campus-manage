package cn.zn.smart.campus.manage.web.controller;

import cn.zn.smart.campus.manage.biz.dto.StudentDTO;
import cn.zn.smart.campus.manage.biz.exception.BizException;
import cn.zn.smart.campus.manage.biz.exception.ErrorEnum;
import cn.zn.smart.campus.manage.biz.service.StudentBizService;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.Student;
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
@RequestMapping("api/student")
public class StudentController {
    @Resource
    private StudentBizService studentBizService;

    @GetMapping("/get/info")
    public Result<Student> getOne(@RequestParam("studentId")String studentId){
        try {
            return Result.succeed(studentBizService.getByStuId(studentId));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @PostMapping("/page/list")
    public Result<ResultPage<Student>> listByPage(@RequestBody PageParam<StudentDTO> param){
        try {
            return Result.succeed(studentBizService.getListByPage(param.getPage(),param.getQueryCon()));
        } catch (IllegalAccessException e) {
            return Result.fail(ErrorEnum.SYS_EXCEPTION.getCode(),e.getMessage());
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }

    }

    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody StudentDTO studentDTO){
        try {
            return Result.succeed(studentBizService.save(studentDTO));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody StudentDTO studentDTO){
        try {
            return Result.succeed(studentBizService.updateBatchByStuId(Lists.newArrayList(studentDTO)));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @PostMapping("/batch/delete")
    public Result<Boolean> delete(@RequestBody List<String> studentIds){
        try {
            return Result.succeed(studentBizService.deleteBatchByStuId(studentIds));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @GetMapping("/get/stuIds")
    public Result<List<String>> getStuIds(){
        try {
            return Result.succeed(studentBizService.getStuIdList());
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }
}
