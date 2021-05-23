package web.service;

import base.BaseTest;
import cn.zn.smart.campus.manage.biz.dto.SecondHandGoodDTO;
import cn.zn.smart.campus.manage.biz.service.SecondHandGoodBizService;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/23 13:01
 */
public class GoodServiceTest extends BaseTest {
    @Resource
    private SecondHandGoodBizService service;

    @Test
    public void saveTest(){
        SecondHandGoodDTO goodDTO = new SecondHandGoodDTO();
        goodDTO.setTitle("这是一条测试数据");
        goodDTO.setIssueStudentId("2017010102");
        service.save(goodDTO);
    }

    @Test
    public void getOneTest(){
        System.out.println(JSON.toJSONString(service.getByGoodId("good_d9bfa1928a0c493fb9162a9df0a31c70")));
    }

    @Test
    public void listTest() throws IllegalAccessException {
        System.out.println(JSON.toJSONString(service.getListByPage(new QueryPage(),null)));
    }

    @Test
    public void deleteTest(){
        service.deleteByGoodId("good_d9bfa1928a0c493fb9162a9df0a31c70");
    }
}
