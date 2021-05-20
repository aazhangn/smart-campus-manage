package web.service;

import base.BaseTest;
import cn.zn.smart.campus.manage.biz.dto.StudentDTO;
import cn.zn.smart.campus.manage.biz.service.StudentBizService;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import com.alibaba.fastjson.JSON;
import org.assertj.core.util.Lists;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/20 18:13
 */
public class StudentServiceTest extends BaseTest {
    @Resource
    private StudentBizService studentBizService;
    @Test
    public void saveTest(){
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("李栋");
        studentDTO.setClassId("20170101");
        studentDTO.setGender(1);
        studentDTO.setNativePalce("河南郑州");
        studentBizService.save(studentDTO);
    }

    @Test
    public void deleteBatchTest(){
        studentBizService.deleteBatchByStuId(Lists.newArrayList("0001"));
    }

    @Test
    public void getOneTest(){
        System.out.println(JSON.toJSONString(studentBizService.getByStuId("2017010101")));
    }

    @Test
    public void updateTest(){
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentId("2017010101");
        studentDTO.setNativePalce("浙江台州");
        studentDTO.setPhoneNumber("14399880065");
        studentBizService.updateBatchByStuId(Lists.newArrayList(studentDTO));
    }

    @Test
    public void listTest() throws IllegalAccessException {
        System.out.println(JSON.toJSONString(studentBizService.getListByPage(new QueryPage(),null)));
    }

}
