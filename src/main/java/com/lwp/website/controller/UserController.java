package com.lwp.website.controller;

import com.lwp.website.entity.Vo.CarouselVo;
import com.lwp.website.res.ResourceManage;
import com.lwp.website.service.impl.UserServiceImpl;
import com.lwp.website.service.wx.Impl.WXCarouselServiceImpl;
import com.lwp.website.service.wx.WXCarouselService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;


@Controller(value = "userController")
@RequestMapping(value = "/user")
public class UserController extends BaseController{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserServiceImpl userService;


    @GetMapping(value = "/add")
    public String add(){
        WXCarouselService commons = ResourceManage.getBean(WXCarouselServiceImpl.class);
        List<CarouselVo> list = commons.getCarouselListByStatus();
        LOGGER.info("添加用户成功");
        userService.insertUser();
        LOGGER.info("跳转到....");
        //UserVo userVo = new UserVo();
        //userVo.setUsername("1");

        //userService.insertUser(userVo);
        return this.render("index");
    }
}
