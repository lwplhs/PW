package com.lwp.blog.controller;

import com.lwp.blog.entity.Vo.UserVo;
import com.lwp.blog.service.UserService;
import com.lwp.blog.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lwp.blog.entity.Users;

import javax.annotation.Resource;


@Controller
public class UserController extends BaseController{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserServiceImpl userService;


    @GetMapping(value = "/add")
    public String add(){
        LOGGER.info("添加用户成功");
        userService.insertUser();
        LOGGER.info("跳转到....");
        //UserVo userVo = new UserVo();
        //userVo.setUsername("1");

        //userService.insertUser(userVo);
        return this.render("index");
    }
}
