package cn.zn.smart.campus.manage.web.controller;

import cn.zn.smart.campus.manage.biz.dto.WeChatUserDTO;
import cn.zn.smart.campus.manage.biz.enums.user.UserRoleEnum;
import cn.zn.smart.campus.manage.biz.exception.BizException;
import cn.zn.smart.campus.manage.biz.exception.ErrorEnum;
import cn.zn.smart.campus.manage.biz.service.UserService;
import cn.zn.smart.campus.manage.dao.po.UserInfo;
import cn.zn.smart.campus.manage.dao.po.WeChatUser;
import cn.zn.smart.campus.manage.web.config.MiniConfig;
import cn.zn.smart.campus.manage.web.param.LoginParam;
import cn.zn.smart.campus.manage.web.param.Tokens;
import cn.zn.smart.campus.manage.web.result.Result;
import cn.zn.smart.campus.manage.web.util.DoubleJWT;
import cn.zn.smart.campus.manage.web.util.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/20 21:04
 */
@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Resource
    private UserService userService;
    @Autowired
    private DoubleJWT jwt;

    @PostMapping("/login")
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

    @PostMapping("/cms/login")
    public Tokens loginCms(@RequestBody LoginParam param) throws Exception {
        try{
            UserInfo userInfo  = userService.login(param.getUsername(), param.getPassword());
            if (userInfo.getRole().equals(UserRoleEnum.ADMIN.getValue())) {
                return jwt.generateTokens(userInfo.getUserId());
            }else{
                throw new BizException(ErrorEnum.SYS_PARAM_ERROR.getCode(),"非管理员无法登录");
            }
        }catch (BizException e){
            throw new Exception(e.getMsg());
        }
    }


    @PostMapping("weChat/login")
    public Result<UserInfo> loginWeChat(@RequestBody LoginParam param) {
        try {
            return Result.succeed(userService.login(param.getUsername(),param.getPassword()));
        }catch (BizException e){
            return Result.fail(e.getCode(), e.getMsg());
        }
    }

    @GetMapping("/cms/get/info")
    public Result<UserInfo> getInfo(@RequestParam("userId") String userId) {
        try {
            return Result.succeed(userService.get(userId));
        }catch (BizException e){
            return Result.fail(e.getCode(), e.getMsg());
        }
    }
}
