package cn.zn.smart.campus.manage.web.controller;

import cn.zn.smart.campus.manage.biz.dto.NoticeDTO;
import cn.zn.smart.campus.manage.biz.exception.BizException;
import cn.zn.smart.campus.manage.biz.exception.ErrorEnum;
import cn.zn.smart.campus.manage.biz.service.NoticeBizService;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.Notice;
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
@RequestMapping("api/notice")
public class NoticeController {
    @Resource
    private NoticeBizService noticeBizService;

    @GetMapping("/get/info")
    public Result<Notice> getOne(@RequestParam("noticeId")String noticeId){
        try {
            return Result.succeed(noticeBizService.getByNoticeId(noticeId));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @PostMapping("/page/list")
    public Result<ResultPage<Notice>> listByPage(@RequestBody PageParam<NoticeDTO> param){
        try {
            return Result.succeed(noticeBizService.getListByPage(param.getPage(),param.getQueryCon()));
        } catch (IllegalAccessException e) {
            return Result.fail(ErrorEnum.SYS_EXCEPTION.getCode(),e.getMessage());
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody NoticeDTO noticeDTO){
        try {
            return Result.succeed(noticeBizService.save(noticeDTO));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody NoticeDTO noticeDTO){
        try {
            return Result.succeed(noticeBizService.updateBatchByNoticeId(Lists.newArrayList(noticeDTO)));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    @PostMapping("/batch/delete")
    public Result<Boolean> delete(@RequestBody List<String> noticeIds){
        try {
            return Result.succeed(noticeBizService.deleteBatchByNoticeId(noticeIds));
        }catch (BizException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }
}