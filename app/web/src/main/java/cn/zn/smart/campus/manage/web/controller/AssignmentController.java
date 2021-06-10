package cn.zn.smart.campus.manage.web.controller;

import cn.zn.smart.campus.manage.biz.dto.AnswerDto;
import cn.zn.smart.campus.manage.biz.dto.AssignmentDto;
import cn.zn.smart.campus.manage.biz.exception.BizException;
import cn.zn.smart.campus.manage.biz.exception.ErrorEnum;
import cn.zn.smart.campus.manage.biz.param.AssignmentMarkParam;
import cn.zn.smart.campus.manage.biz.service.AssignmentBizService;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.Assignment;
import cn.zn.smart.campus.manage.dao.po.StuAssRel;
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
@RequestMapping("api/assignment")
public class AssignmentController {
    @Resource
    private AssignmentBizService assignmentBizService;

    @GetMapping("/get/info")
    public Result<Assignment> getOne(@RequestParam("assignmentId")String assignmentId){
        try {
            return Result.succeed(assignmentBizService.getByAssId(assignmentId));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }
    @GetMapping("/list/byTeaId")
    public Result<List<Assignment>> getListByTeaId(@RequestParam("teacherId")String teacherId){
        try {
            return Result.succeed(assignmentBizService.getAssListByTeaId(teacherId));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @GetMapping("/answer/list/byAssId")
    public Result<List<StuAssRel>> getAnswerListByAssId(@RequestParam("assignmentId")String assignmentId){
        try {
            return Result.succeed(assignmentBizService.getAnswerListByAssId(assignmentId));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @PostMapping("/page/list")
    public Result<ResultPage<Assignment>> listByPage(@RequestBody PageParam<AssignmentDto> param){
        try {
            return Result.succeed(assignmentBizService.getListByPage(param.getPage(), param.getQueryCon()));
        } catch (IllegalAccessException e) {
            return Result.fail(ErrorEnum.SYS_EXCEPTION.getCode(),e.getMessage());
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody AssignmentDto assignmentDto){
        try {
            return Result.succeed(assignmentBizService.save(assignmentDto));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody AssignmentDto assignmentDto){
        try {
            return Result.succeed(assignmentBizService.updateBatchByAssId(Lists.newArrayList(assignmentDto)));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @PostMapping("/batch/delete")
    public Result<Boolean> delete(@RequestBody List<String> assIds){
        try {
            return Result.succeed(assignmentBizService.deleteBatchByAssId(assIds));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @PostMapping("/answer/submit")
    public Result<Boolean> submitAnswer(@RequestBody AnswerDto answerDto){
        try {
            return Result.succeed(assignmentBizService.submitAnswer(answerDto));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @PostMapping("/answer/mark")
    public Result<Boolean> markAnswer(@RequestBody AssignmentMarkParam param){
        try {
            return Result.succeed(assignmentBizService.markAssignment(param));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }
}
