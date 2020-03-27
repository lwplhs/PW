package com.lwp.blog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController extends BaseController{
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @GetMapping(value = "/chat")
    public String chat(){
        LOGGER.info("跳转到聊天室");
        return this.render("chat_room");
    }
}
