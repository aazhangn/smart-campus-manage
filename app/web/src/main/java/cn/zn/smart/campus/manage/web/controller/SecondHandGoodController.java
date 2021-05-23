package cn.zn.smart.campus.manage.web.controller;

import cn.zn.smart.campus.manage.biz.dto.SecondHandGoodDTO;
import cn.zn.smart.campus.manage.biz.exception.BizException;
import cn.zn.smart.campus.manage.biz.exception.ErrorEnum;
import cn.zn.smart.campus.manage.biz.service.SecondHandGoodBizService;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.SecondHandGood;
import cn.zn.smart.campus.manage.web.param.PageParam;
import cn.zn.smart.campus.manage.web.result.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/23 13:27
 */
@RestController
@RequestMapping("api/good")
public class SecondHandGoodController {
    @Resource
    private SecondHandGoodBizService service;

    @GetMapping("/get/info")
    public Result<SecondHandGood> getOne(@RequestParam("goodId")String goodId){
        try {
            return Result.succeed(service.getByGoodId(goodId));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @PostMapping("/page/list")
    public Result<ResultPage<SecondHandGood>> listByPage(@RequestBody PageParam<SecondHandGoodDTO> param){
        try {
            return Result.succeed(service.getListByPage(param.getPage(),param.getQueryCon()));
        } catch (IllegalAccessException e) {
            return Result.fail(ErrorEnum.SYS_EXCEPTION.getCode(),e.getMessage());
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }

    }

    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody SecondHandGoodDTO secondHandGoodDTO){
        try {
            return Result.succeed(service.save(secondHandGoodDTO));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody SecondHandGoodDTO secondHandGoodDTO){
        try {
            return Result.succeed(service.updateByGoodId(secondHandGoodDTO));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @PostMapping("/batch/delete")
    public Result<Boolean> delete(@RequestBody List<String> goodIds){
        try {
            return Result.succeed(service.deleteBatchByGoodId(goodIds));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }
}