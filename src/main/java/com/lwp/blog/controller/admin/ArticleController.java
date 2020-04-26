package com.lwp.blog.controller.admin;

import com.lwp.blog.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/04/26/14:45
 * @Description:
 */

@Controller("adminArticleController")
@RequestMapping("/admin")
public class ArticleController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);


    @PostMapping(value = "/saveArticle")
    @ResponseBody
    public String saveArticle(){

        return "";
    }

}
