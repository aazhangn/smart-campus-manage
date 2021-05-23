package web.service;

import base.BaseTest;
import cn.zn.smart.campus.manage.biz.service.UserService;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/22 11:01
 */
public class UserServiceTest extends BaseTest {
    @Resource
    private UserService userService;
    @Test
    public void saveTest(){
        userService.saveAdmin("012101003");
    }
}
