package com.registration.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.registration.entity.Result;
import com.registration.entity.User;
import com.registration.entity.WXAuth;
import com.registration.mapper.UserMapper;
import com.registration.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ljx
 * @since 2022-10-03
 */
@RestController
@RequestMapping("/user")
public class UserController {



    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/getSessionId")
    @ApiOperation(value = "获取SessionId(先)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "code", value = "code", required = true, dataType = "String"),
         })
    public Result getSessionId(String code) {
        Result result = new Result();
        String sessionId = userService.getSessionId(code);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("sessionId", sessionId);
        result.setCode(200);
        result.setData(hashMap);
        return result;
    }

    @PostMapping("/authLogin")
    @ApiOperation(value = "微信登录（后）")
    public Result authLogin(@RequestBody WXAuth wxAuth) {
        Result result = userService.authLogin(wxAuth);
        //log.info("{}",result);
        return result;
    }
//    @PostMapping("/login")
//    @ApiOperation(value = "随便登录")
//    public Result login(@RequestBody String name) {
//        Result result = new Result();
//        //log.info("{}",result);
//        QueryWrapper qw = new QueryWrapper();
//        qw.eq("name",name);
//        User user = userMapper.selectOne(qw);
//        result.setData(user);
//        result.setCode(200);
//        return result;
//    }
//

    @PostMapping("/setmessagebyid")
    @ApiOperation(value = "设置学号、姓名")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "id", value = "id", required = true,dataType = "int"),
            @ApiImplicitParam(paramType = "body", name = "name", value = "姓名", required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "body", name = "number", value = "学号", required = false,dataType = "String")
    })
    public Result setMessage(@ApiIgnore @RequestBody User user){
        System.out.println(user.getId());
        System.out.println(user.getName());
        System.out.println(user.getNumber());
        Result result = userService.setMessage(user.getId(), user.getName(),user.getNumber());
        return result;
    }

    @GetMapping("/getstudent")
    @ApiOperation(value = "获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "id", value = "id", required = true,dataType = "int"),
              })
    public Result getStudent(Integer id){
        Result result = userService.getStudent(id);
        return result;
    }

}
