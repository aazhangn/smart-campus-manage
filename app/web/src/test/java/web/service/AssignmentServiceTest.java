package web.service;

import base.BaseTest;
import cn.zn.smart.campus.manage.biz.dto.AssignmentDto;
import cn.zn.smart.campus.manage.biz.enums.assignment.AssignmentTypeEnum;
import cn.zn.smart.campus.manage.biz.service.AssignmentBizService;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/18 21:53
 */
public class AssignmentServiceTest extends BaseTest {
    @Resource
    private AssignmentBizService assignmentBizService;

    @Test
    public void saveTest(){
        AssignmentDto assignmentDto = new AssignmentDto();
        assignmentDto.setSubject(AssignmentTypeEnum.COMPLETION.getValue());
        assignmentDto.setSubject("语文");
        assignmentBizService.save(assignmentDto);
    }

    @Test
    public void deleteBatchTest(){
        assignmentBizService.deleteBatchByAssId(Lists.newArrayList("6000000261218547"));
    }

    @Test
    public void updateBatchTest(){
        AssignmentDto assignmentDto = new AssignmentDto();
        assignmentDto.setAssignmentId("6000001963149752");
        assignmentDto.setSubject("数学");
        assignmentBizService.updateBatchByAssId(Lists.newArrayList(assignmentDto));
    }

    @Test
    public void getListByPageTest() throws IllegalAccessException {
        AssignmentDto assignmentDto = new AssignmentDto();
        assignmentDto.setSubject("数学");
        System.out.println(JSON.toJSONString(assignmentBizService.getListByPage(new QueryPage(),assignmentDto)));
    }

    @Test
    public void getByAssIdTest(){
        System.out.println(JSON.toJSONString(assignmentBizService.getByAssId("6000000261218547")));
    }
}
