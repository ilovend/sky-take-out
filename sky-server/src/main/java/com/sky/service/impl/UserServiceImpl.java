package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * 用户服务impl
 *
 * @author ilovend
 * @date 2023/04/25
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * wx服务接口地址
     */
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    /**
     * 用户映射器
     */
    @Autowired
    private UserMapper userMapper;
    /**
     * wechat属性
     */
    @Autowired
    private WeChatProperties weChatProperties;

    /**
     * 微信登录
     *
     * @param userLoginDTO 用户登录dto
     * @return {@link User}
     */
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        String openid = getOpenid(userLoginDTO.getCode());

//        判断openid是否为空,如果为空表示登录失败,并抛出业务异常
        if (openid == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
//        判断当前用户是否为新用户
        User user = userMapper.getByOpenid(openid);
//        如果是新用户,自动完成注册
        if (user == null) {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }
        return user;
    }

    /**
     * 调用微信接口,获取微信用户的openid
     *
     * @param code 代码
     * @return {@link String}
     */
    private String getOpenid(String code) {
        HashMap<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");

        String json = HttpClientUtil.doGet(WX_LOGIN, map);
        JSONObject jsonObject = JSON.parseObject(json);
        return jsonObject.getString("openid");
    }
}
