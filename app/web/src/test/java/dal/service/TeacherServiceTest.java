package dal.service;

import base.BaseTest;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import cn.zn.smart.campus.manage.dao.po.TeacherInfo;
import cn.zn.smart.campus.manage.dao.service.ITeacherInfoService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @Author: zhangnan
 * @Date: 2021/05/13 13:32
 */
public class TeacherServiceTest extends BaseTest {

    @Resource
    private ITeacherInfoService iTeacherInfoService;

    @Test
    public void getByIdTest(){
        TeacherInfo teacherInfo = iTeacherInfoService.getByEntityId("1111111","teacherId");
        System.out.println(JSON.toJSONString(teacherInfo));
    }

    @Test
    public void saveBatchTest(){
        TeacherInfo teacherInfo = new TeacherInfo();
        teacherInfo.setTeacherId("1111111");
        teacherInfo.setName("张三");
        iTeacherInfoService.saveBatch(Lists.newArrayList(teacherInfo));
    }

    @Test
    public void updateBatchTest(){
        TeacherInfo teacherInfo = new TeacherInfo();
        teacherInfo.setId(Long.parseLong("1"));
        teacherInfo.setTeacherId("1111111");
        teacherInfo.setName("张三");
        iTeacherInfoService.updateBatchById(Lists.newArrayList(teacherInfo));
    }

    @Test
    public void updateBatchByTeaIdTest(){
        TeacherInfo teacherInfo = new TeacherInfo();
        teacherInfo.setTeacherId("");
        teacherInfo.setName("李四");
        teacherInfo.setAddress("甘肃省庆阳市西峰区北大街111号");
        iTeacherInfoService.updateBatchByCondition(Lists.newArrayList(teacherInfo),"teacherId");
    }

    @Test
    public void getTeacherListByPageTest(){
        Object obj = iTeacherInfoService.getEntityListByPage(new QueryPage(1,2), null);
        System.out.println(JSON.toJSONString(obj));
    }


}
