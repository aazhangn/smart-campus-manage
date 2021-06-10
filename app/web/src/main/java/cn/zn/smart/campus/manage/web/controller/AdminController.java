package cn.zn.smart.campus.manage.web.controller;

import cn.zn.smart.campus.manage.biz.dto.AdminiDTO;
import cn.zn.smart.campus.manage.biz.exception.BizException;
import cn.zn.smart.campus.manage.biz.service.UserService;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.UserInfo;
import cn.zn.smart.campus.manage.web.result.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/23 13:27
 */
@RestController
@RequestMapping("api/admin")
public class AdminController {
    @Resource
    private UserService userService;

    @PostMapping("/page/list")
    public Result<ResultPage<UserInfo>> listByPage(@RequestBody QueryPage param) {
        try {
            return Result.succeed(userService.listByPage(param));
        } catch (BizException e) {
            return Result.fail(e.getCode(), e.getMsg());
        }
    }

    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody AdminiDTO admin){
        try {
            return Result.succeed(userService.updateAdmin(admin));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @GetMapping("/save")
    public Result<Boolean> save(@RequestParam("teacherId") String teacherId) {
        try {
            return Result.succeed(userService.saveAdmin(teacherId));
        } catch (BizException e) {
            return Result.fail(e.getCode(), e.getMsg());
        }
    }

    @GetMapping("/delete")
    public Result<Boolean> delete(@RequestParam("adminId") String administratorId) {
        try {
            return Result.succeed(userService.deleteAdmin(administratorId));
        } catch (BizException e) {
            return Result.fail(e.getCode(), e.getMsg());
        }
    }
}
