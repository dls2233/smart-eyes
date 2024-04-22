package com.example.smarteyes.controller;

import com.example.smarteyes.common.util.Md5Utils;
import com.example.smarteyes.domain.pojo.User;
import com.example.smarteyes.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.smarteyes.common.Result;

import java.util.Objects;

/**
 * @author 黄一峰
 * @date 2024/4/16 10:19
 * @description LoginController
 */
@RestController
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    public LoginMapper loginMapper;


    @GetMapping("/login")
    public Result login(@RequestParam String userName, @RequestParam String passWord){
        try {
            User user = loginMapper.selectUserByName(userName);
            if(!Objects.isNull(user) && Objects.equals(user.getPassWord(), Md5Utils.hash(passWord))){
                return Result.success("登录成功");
            }else{
                return Result.error("用户名或密码错误");
            }
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
