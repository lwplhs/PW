package com.lwp.blog.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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

    @GetMapping(value = "/article-list")
    public String getArticle(Model model,
                            @RequestParam(value="pageNum",defaultValue = "1") int pageNum,
                            @RequestParam(value = "limit",defaultValue = "10") int limit){
        Page<ContentVo> page = PageHelper.startPage(pageNum,limit);
        List<ContentVo> list = articleService.listArticle();

        model.addAttribute("total",page.getTotal());
        model.addAttribute("article",list);
        return this.render("/admin/article-list");
    }

    @PostMapping(value = "/saveArticle")
    @ResponseBody
    public String saveArticle(@ModelAttribute ContentVo contentVo){
        articleService.insertArticle(contentVo);
        return "成功";
    }
    @PostMapping(value = "/list")
    public String listArticle(Model model,
                              @RequestParam(value="pageNum",defaultValue = "1") int pageNum,
                              @RequestParam(value = "limit",defaultValue = "10") int limit){
        Page<ContentVo> page = PageHelper.startPage(pageNum,limit);
        List<ContentVo> list = articleService.listArticle();
        model.addAttribute("total",page.getTotal());
        model.addAttribute("article",list);
        return this.render("/admin/article-list::article_type");
    }

}
