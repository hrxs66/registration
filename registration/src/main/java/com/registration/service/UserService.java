package com.registration.service;

import com.registration.entity.Result;
import com.registration.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.registration.entity.WXAuth;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ljx
 * @since 2022-10-03
 */
public interface UserService extends IService<User> {

    String getSessionId(String code);


    Result authLogin(WXAuth wxAuth);

    Result setMessage(Integer openid, String name, String number);

    Result getStudent(Integer id);


}
