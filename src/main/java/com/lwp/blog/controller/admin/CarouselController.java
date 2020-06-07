package com.lwp.blog.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lwp.blog.controller.BaseController;
import com.lwp.blog.entity.Bo.RestResponseBo;
import com.lwp.blog.entity.Vo.CarouselVo;
import com.lwp.blog.entity.Vo.ContentVo;
import com.lwp.blog.entity.Vo.UserVo;
import com.lwp.blog.service.CarouselService;
import com.lwp.blog.utils.InvalidUtil;
import com.lwp.blog.utils.TaleUtils;
import com.lwp.blog.utils.invalid.carousel.CarouselValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/04/15:31
 * @Description:
 */
@Controller("carouselController")
@RequestMapping(value = "/admin/carousel")
public class CarouselController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarouselController.class);

    @Resource
    private CarouselService carouselService;

    /**
     * 添加首页轮播图
     * @param request
     * @param response
     * @param carouselVo
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "saveCarousel")
    @ResponseBody
    public RestResponseBo SaveCarousel(HttpServletRequest request,
                               HttpServletResponse response,
                               @Valid @ModelAttribute CarouselVo carouselVo,
                               BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return InvalidUtil.error(LOGGER,bindingResult);
        }
        LOGGER.info("-------------------参数校验成功------------------");
        LOGGER.info("-------------------开始保存首页轮播图------------------");
        UserVo userVo = TaleUtils.getLoginUser(request);
        String result = carouselService.saveCarousel(carouselVo,userVo);
        LOGGER.info("-------------------结束保存首页轮播图------------------");
        return RestResponseBo.ok(result);
    }

    /**
     * 更新首页轮播图
     * @param request
     * @param response
     * @param carouselVo
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "updateCarousel")
    @ResponseBody
    public RestResponseBo UpdateCarousel(HttpServletRequest request,
                               HttpServletResponse response,
                               @Validated(CarouselValidation.GroupCarouselEdit.class) @ModelAttribute CarouselVo carouselVo,
                               BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return InvalidUtil.error(LOGGER,bindingResult);
        }
        LOGGER.info("-------------------参数校验成功------------------");
        LOGGER.info("-------------------开始更新首页轮播图------------------");
        UserVo userVo = TaleUtils.getLoginUser(request);
        String result = carouselService.saveCarousel(carouselVo,userVo);
        LOGGER.info("-------------------结束更新首页轮播图------------------");
        return RestResponseBo.ok(result);
    }


    /**跳转到首页轮播图列表 -分页查询数据
     *
     * @param model
     * @param pageNum
     * @param limit
     * @return
     */
    @GetMapping(value = "/carousel-list")
    public String toPagePictureList(Model model,
                                     @RequestParam(value="pageNum", defaultValue = "1") int pageNum,
                                     @RequestParam(value = "limit",defaultValue = "10") int limit){

        Page<ContentVo> page = PageHelper.startPage(pageNum,limit);
        List<CarouselVo> list = carouselService.getListCarousel();

        model.addAttribute("picList",list);
        model.addAttribute("total",page.getTotal());
        return this.render("/admin/carousel/carousel-list");
    }

    /**
     * 查看页面
     * @param model
     * @param id
     * @return
     */
    @GetMapping(value = "/carousel-view")
    public String toPageCarouselView(Model model,
                                      @RequestParam(value = "id",defaultValue = "") String id){
        CarouselVo carouselVo = carouselService.getCarouselById(id);
        model.addAttribute("Carousel",carouselVo);
        return this.render("/admin/carousel/carousel-view");
    }

    /**
     * 跳转到新增页面
     * @return
     */
    @GetMapping(value = "/carousel-add")
    public String toPageCarouselAdd(){
        return this.render("/admin/carousel/carousel-add");
    }
    /**
     * 跳转到更新页面 -根据id查询数据填充
     * @param model
     * @param id
     * @return
     */
    @GetMapping(value = "/carousel-edit")
    public String toPageCarouselEdit(Model model,
                                     @RequestParam(value = "id",defaultValue = "") String id){
        CarouselVo carouselVo = carouselService.getCarouselById(id);
        model.addAttribute("Carousel",carouselVo);
        return this.render("/admin/carousel/carousel-edit");
    }
    /**
     * 根据分页 获取轮播图数据
     * @param model
     * @param pageNum
     * @param limit
     * @return
     */
    @PostMapping(value = "/getCarousel")
    public String getCarousel(Model model,
                              @RequestParam(value="pageNum",defaultValue = "1") int pageNum,
                              @RequestParam(value = "limit",defaultValue = "10") int limit){
        LOGGER.info("-------------------获取首页轮播图数据 第"+pageNum+"页，"+limit+"条数据------------------");
        Page<ContentVo> page = PageHelper.startPage(pageNum,limit);
        List<CarouselVo> list = carouselService.getListCarousel();
        LOGGER.info("-------------------获取首页轮播图数据结束------------------");
        model.addAttribute("picList",list);
        model.addAttribute("total",page.getTotal());
        return this.render("/admin/carousel/carousel-list::list");
    }

    /**
     * 根据id更新数据
     * @param ids
     * @param type 1 状态status
     * @return
     */
    @PostMapping(value = "/updateCarouselStatus")
    @ResponseBody
    public RestResponseBo updateCarouse(@RequestParam(value = "ids") String ids,
                                @RequestParam(value = "type") String type,
                                HttpServletRequest request){
        LOGGER.info("-------------------修改轮播图数据------------------");
        UserVo userVo = TaleUtils.getLoginUser(request);
        Boolean bool = carouselService.updateCarousel(ids,type,userVo);
        if(bool){
            return RestResponseBo.ok(1,"更新成功");
        }else {
            return RestResponseBo.fail(-1,"更新失败，请刷新数据");
        }
    }
}
