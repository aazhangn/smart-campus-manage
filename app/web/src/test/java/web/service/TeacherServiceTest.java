package web.service;

import base.BaseTest;
import cn.zn.smart.campus.manage.biz.dto.TeacherDTO;
import cn.zn.smart.campus.manage.biz.enums.teacher.HireTypeEnum;
import cn.zn.smart.campus.manage.biz.enums.teacher.TeaResearchGroupEnum;
import cn.zn.smart.campus.manage.biz.service.TeacherService;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/16 00:10
 */
public class TeacherServiceTest extends BaseTest {
    @Resource
    private TeacherService teacherService;

    @Test
    public void getTeacherListByPageTest() throws IllegalAccessException {
        TeacherDTO teacherDto = new TeacherDTO();
        Object obj = teacherService.getTeacherListByPage(new QueryPage(1,3),teacherDto);
        System.out.println(JSON.toJSONString(obj));
    }

    @Test
    public void deleteBatchByTeaIdTest(){
        System.out.println(teacherService.deleteBatchByTeaId(Lists.newArrayList("0101002","0101001")));
    }

    @Test
    public void updateBatchTest(){
        TeacherDTO teacherDto = new TeacherDTO();
        teacherDto.setTeacherId("0101002");
        teacherDto.setName("李大毛");
        teacherService.updateBatchByTeaId(Lists.newArrayList(teacherDto));
    }

    @Test
    public void saveTest(){
        TeacherDTO teacher = new TeacherDTO();
        teacher.setHireType(HireTypeEnum.PERMANENT.getDesc());
        teacher.setIdNumber("622809778868095698");
        teacher.setTeaResearchGroup(TeaResearchGroupEnum.JHS.getValue());
        teacher.setName("刘静");
        teacherService.save(teacher);
    }
}
