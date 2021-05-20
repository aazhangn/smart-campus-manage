package cn.zn.smart.campus.manage.web.controller;

import cn.zn.smart.campus.manage.biz.dto.WeChatUserDTO;
import cn.zn.smart.campus.manage.biz.service.UserService;
import cn.zn.smart.campus.manage.dao.po.WeChatUser;
import cn.zn.smart.campus.manage.web.config.MiniConfig;
import cn.zn.smart.campus.manage.web.result.Result;
import cn.zn.smart.campus.manage.web.util.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/20 21:04
 */
@RequestMapping("/api/user/manage")
public class UserController {

    @Resource
    private UserService userService;
    @PostMapping("/weChat/login")
    @ResponseBody
    public Result<JSONObject> loginWeChat(HttpServletRequest request) {
        String code =request.getParameter("code");
        JSONObject resultJson = new JSONObject();
        String result = "";
        try {//请求微信服务器，用code换取openid。HttpUtil是工具类，后面会给出实现，Configure类是小程序配置信息，后面会给出代码
            result = HttpUtil.doGet(
                    "https://api.weixin.qq.com/sns/jscode2session?appid="
                            + MiniConfig.mini_appID + "&secret="
                            + MiniConfig.mini_secret + "&js_code="
                            + code
                            + "&grant_type=authorization_code", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //解析从微信服务器上获取到的json字符串
        //此处也可以得到session_key的值
        JSONObject jsonObj = JSONObject.parseObject(result);
        resultJson.put("session_key", jsonObj.get("session_key").toString());
        //去数据库判断用户是否存在该用户
        WeChatUser weChatUser  = userService.isRegister(jsonObj.get("openid").toString());
        //如果存在该用户
        if (Objects.isNull(weChatUser)){
            //将用户id返回
            resultJson.put("phoneNumber", weChatUser.getPhoneNumber());
            resultJson.put("role",weChatUser.getRole());
            resultJson.put("mapId",weChatUser.getMapId());
            return Result.succeed(resultJson);
        }

        //如果是新用户，就添加用户到数据库中
        WeChatUserDTO user = new WeChatUserDTO();
        user.setOpenId(jsonObj.get("openid").toString());
//        user.setPhoneNumber(jsonObj.get(""));
        //将用户信息保存到数据库中
        userService.updateWeChatUser(user);
        return null;
    }
}
