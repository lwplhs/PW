package com.lwp.blog.controller.admin;

import com.lwp.blog.controller.BaseController;
import com.lwp.blog.entity.Vo.ContentVo;
import com.lwp.blog.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

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

    @Resource
    private ArticleService articleService;

    @PostMapping(value = "/saveArticle")
    @ResponseBody
    public String saveArticle(@ModelAttribute ContentVo contentVo){
        articleService.insertArticle(contentVo);
        return "wwww";
    }

    @PostMapping(value = "/list")
    public String listArticle(Model model){
        List<ContentVo> list = articleService.listArticle();
        model.addAttribute("article",list);
        return this.render("/admin/article-list::article_type");
    }

}
