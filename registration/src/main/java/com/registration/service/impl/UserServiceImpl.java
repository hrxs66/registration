package com.registration.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.registration.entity.*;
import com.registration.mapper.UserMapper;
import com.registration.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ljx
 * @since 2022-10-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private WxService wxService;
    @Autowired
    private UserMapper userMapper;


    private String secret = "26b3d37dc76cc2a622674ee16005ff05";
    private String appid = "wxbfda68feb3d17b30";

    public String getSessionId(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={0}&secret={1}&js_code={2}&grant_type=authorization_code";
        url = url.replace("{0}", appid).replace("{1}", secret).replace("{2}", code);
        String res = HttpUtil.get(url);
        //System.out.println(res);
        String s = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set("WX_SESSION_ID" + s, res);
        return s;
    }


    public UserDto login(UserDto userDto) {
        // 登录成功 封装用户信息到token
        userDto.setPassword(null);
        userDto.setName(null);
        userDto.setOpenId(null);
        userDto.setWxUnionId(null);
        //String token = JWTUtils.sign(userDto.getId());
        //userDto.setToken(token);
        //保存到redis内,下次就直接跳过验证
       // redisTemplate.opsForValue().set(RedisKey.TOKEN_KEY + token, JSON.toJSONString(userDto), 7, TimeUnit.DAYS);
        return userDto;
    }

    public Result register(UserDto userDto) {
        User user = new User();
        Result result = new Result();
        BeanUtils.copyProperties(userDto,user);
        User queryUser = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getOpenId, user.getOpenId()));
        if (queryUser == null) {
            userMapper.insert(user);
        }
        result.setCode(200);
        //已存在直接登录
        result.setData(login(userDto));
        return  result;

    }

    public Result authLogin(WXAuth wxAuth) {
        Result result = new Result();
        try {

            String wxRes = wxService.wxDecrypt(wxAuth.getEncryptedData(), wxAuth.getSessionId(), wxAuth.getIv());
            WxUserInfo wxUserInfo = JSON.parseObject(wxRes,WxUserInfo.class);

            User user = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getOpenId, wxUserInfo.getOpenId()));
            if (user != null) {
                //登录成功
                UserDto userDto = new UserDto();
                BeanUtils.copyProperties(user,userDto);
                result.setCode(200);
                result.setData(this.login(userDto));
                return result;
            } else {
                UserDto userDto = new UserDto();
                userDto.from(wxUserInfo);
                return this.register(userDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setCode(404);
        return result;
    }

    @Override
    public Result setMessage(Integer id, String name, String number) {
        Result result = new Result();
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("id",id);
        User user = userMapper.selectOne(qw);
        Duration duration = Duration.between(user.getUpdateTime(), LocalDateTime.now());
        if (user.getName()!=null && duration.toDays()<30){
            result.setCode(404);
            result.setStatus("修改间隔过短");
            return result;
        }
        UpdateWrapper<User> uw = new UpdateWrapper<>();
        uw.eq("id",id);
        uw.set("name",name);
        uw.set("number",number);
        uw.set("update_time", LocalDateTime.now());
        System.out.println(11111);
        userMapper.update(null,uw);
        result.setCode(200);
        return result;

    }

    @Override
    public Result getStudent(Integer id) {
        Result result = new Result();
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("id",id );

        User user1 = userMapper.selectOne(qw);
        result.setData(user1);
        result.setCode(200);
        return result;
    }

}
