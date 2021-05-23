package cn.zn.smart.campus.manage.web.controller;

import cn.zn.smart.campus.manage.biz.dto.ParentDto;
import cn.zn.smart.campus.manage.biz.exception.BizException;
import cn.zn.smart.campus.manage.biz.exception.ErrorEnum;
import cn.zn.smart.campus.manage.biz.service.ParentBizService;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
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
@RequestMapping("api/parent")
public class ParentController {
    @Resource
    private ParentBizService service;

    @GetMapping("/get/info")
    public Result<ParentDto> getOne(@RequestParam("parentId") String parentId) {
        try {
            return Result.succeed(service.getByParId(parentId));
        } catch (BizException e) {
            return Result.fail(e.getCode(), e.getMsg());
        }
    }

    @PostMapping("/page/list")
    public Result<ResultPage<ParentDto>> listByPage(@RequestBody PageParam<ParentDto> param) {
        try {
            return Result.succeed(service.getListByPage(param.getPage(), param.getQueryCon()));
        } catch (IllegalAccessException e) {
            return Result.fail(ErrorEnum.SYS_EXCEPTION.getCode(), e.getMessage());
        } catch (BizException e) {
            return Result.fail(e.getCode(), e.getMsg());
        }
    }

    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody ParentDto parentDto) {
        try {
            return Result.succeed(service.save(parentDto));
        } catch (BizException e) {
            return Result.fail(e.getCode(), e.getMsg());
        }
    }

    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody ParentDto parentDto) {
        try {
            return Result.succeed(service.updateBatchByParId(Lists.newArrayList(parentDto)));
        } catch (BizException e) {
            return Result.fail(e.getCode(), e.getMsg());
        }
    }

    @PostMapping("/batch/delete")
    public Result<Boolean> delete(@RequestBody List<String> parentIds) {
        try {
            return Result.succeed(service.deleteBatch(parentIds));
        } catch (BizException e) {
            return Result.fail(e.getCode(), e.getMsg());
        }
    }
}
