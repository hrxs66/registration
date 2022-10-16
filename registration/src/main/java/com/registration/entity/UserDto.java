package com.registration.entity;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {

    private Integer id;
    private String name;

    private String number;

    private String password;
    private Integer type;
    private String gender;
    @NotNull
    private String phoneNumber;
    /**
     * 背景图片
     */
    private String background;
    private String portrait;

    private String openId;

    private String wxUnionId;

    //dto拓展属性
    private String token;
    List<String> permissions;
    List<String> roles;
    //验证码
    private String code;

    public void from(WxUserInfo wxUserInfo) {
        this.name = wxUserInfo.getNickName();
        this.portrait = wxUserInfo.getAvatarUrl();
        this.number = "";
        this.password = "";
        this.phoneNumber = "";
        this.gender = wxUserInfo.getGender();
        this.openId = wxUserInfo.getOpenId();
        this.wxUnionId = wxUserInfo.getUnionId();
    }
}
