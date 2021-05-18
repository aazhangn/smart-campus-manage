package web.service;

import base.BaseTest;
import cn.zn.smart.campus.manage.biz.dto.ClassDto;
import cn.zn.smart.campus.manage.biz.service.ClassService;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import com.alibaba.fastjson.JSON;
import org.assertj.core.util.Lists;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/18 16:05
 */
public class ClassServiceTest extends BaseTest {
    @Resource
    private ClassService classService;
    @Test
    public void getClassListByTeaId(){
        System.out.println(JSON.toJSONString(classService.getClassListByTeaId("0101001")));
    }

    @Test
    public void saveTest(){
        ClassDto classDto = new ClassDto();
        classDto.setGrade("2017");
        classDto.setClassNo("01");
        classService.save(classDto);
    }

    @Test
    public void deleteBatchTest(){
        classService.deleteBatchByClaId(Lists.newArrayList("201706","201802"));
    }

    @Test
    public void updateBatchTest(){
        ClassDto classDto = new ClassDto();
        classDto.setClassId("201706");
        classDto.setName("201706");
        classService.updateBatchByClaId(Lists.newArrayList(classDto));
    }

    @Test
    public void getListByPageTest() throws IllegalAccessException {
        System.out.println(JSON.toJSONString(classService.getListByPage(new QueryPage(),null)));
    }

    @Test
    public void getByIdTest(){
        System.out.println(JSON.toJSONString(classService.getByClaId("201706")));
    }
}
