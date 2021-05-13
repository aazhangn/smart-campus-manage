package base;

import cn.zn.smart.campus.manage.web.WebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @Description: 测试基类
 * @Author: zhangnan
 * @Date: 2021/05/13 13:30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebApplication.class)
public class BaseTest {

    @Test
    public void run(){
        System.out.println("单元测试基础父类");
    }

}
