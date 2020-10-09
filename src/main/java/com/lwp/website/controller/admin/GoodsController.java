package com.lwp.website.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lwp.website.controller.BaseController;
import com.lwp.website.entity.Vo.ContentVo;
import com.lwp.website.entity.Vo.GoodsCarouselVo;
import com.lwp.website.entity.Vo.GoodsMainVo;
import com.lwp.website.service.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/09/22/16:20
 * @Description:
 */
@Controller
@RequestMapping(value = "/admin/goods")
public class GoodsController extends BaseController {


    Logger LOGGER = LoggerFactory.getLogger(GoodsController.class);


    @Resource
    private GoodsService goodsService;

    @GetMapping(value = "/manage")
    public String manager(){
        return this.render("/admin/product/goods/goods-list");
    }
    @GetMapping(value = "/goods-add")
    public String goodsAdd(){
        return this.render("/admin/product/goods/goods-add");
    }
    @GetMapping(value = "/goods-edit")
    public String goodsEdit(){
        return this.render("/admin/product/goods/goods-edit");
    }

    @GetMapping(value = "/goods-view")
    public String goodsView(){
        return this.render("/admin/product/goods/goods-view");
    }

    @PostMapping(value = "/getGoods")
    public String getGoods(Model model,
                           @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                           @RequestParam(value = "limit",defaultValue = "10") int limit){

        LOGGER.info("-------------------获取商品数据 第"+pageNum+"页，"+limit+"条数据------------------");
        Page<GoodsMainVo> page = PageHelper.startPage(pageNum,limit);
        List<GoodsMainVo> list = goodsService.getListCarousel();
        LOGGER.info("-------------------获取商品数据结束------------------");
        model.addAttribute("list",list);
        model.addAttribute("total",page.getTotal());
        return this.render("/admin/product/goods/goods-list::list");
    }


}
