package com.lwp.blog.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lwp.blog.controller.BaseController;
import com.lwp.blog.entity.Vo.CarouselVo;
import com.lwp.blog.entity.Vo.ContentVo;
import com.lwp.blog.entity.Vo.UserVo;
import com.lwp.blog.service.CarouselService;
import com.lwp.blog.utils.StringUtil;
import com.lwp.blog.utils.TaleUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/04/15:31
 * @Description:
 */
@Controller("carouselController")
@RequestMapping(value = "/admin")
public class CarouselController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarouselController.class);

    @Resource
    private CarouselService carouselService;


    /**
     * webupload上传图片方法
     * @param files MultipartFile
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "uploadCarousel")
    @ResponseBody
    public String UploadCarousel(@RequestParam("file") MultipartFile files,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
        JSONObject json=new JSONObject();
        response.setCharacterEncoding("utf-8");
        LOGGER.info("-------------------开始调用上传文件upload接口-------------------");
        UserVo userVo = TaleUtils.getLoginUser(request);
        Map map = carouselService.UploadCarousel(files,userVo);
        LOGGER.info("-------------------结束调用上传文件upload接口-------------------");
        json = (JSONObject) JSONObject.toJSON(map);
        return json.toString();
    }


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
    public String SaveCarousel(HttpServletRequest request,
                               HttpServletResponse response,
                               @Valid @ModelAttribute CarouselVo carouselVo,
                               BindingResult bindingResult){
        JSONObject jsonObject = new JSONObject();
        if(bindingResult.hasErrors()){
            String msg = bindingResult.getFieldError().getDefaultMessage();
            jsonObject.put("code","111111");
            jsonObject.put("msg",msg);
            LOGGER.info(msg);
            return jsonObject.toString();
        }
        LOGGER.info("-------------------参数校验成功------------------");
        LOGGER.info("-------------------开始新增首页轮播图------------------");
        UserVo userVo = TaleUtils.getLoginUser(request);
        String result = carouselService.insertCarousel(carouselVo,userVo);
        LOGGER.info("-------------------结束新增首页轮播图------------------");
        return result;
    }

    /**跳转到首页轮播图列表 -分页查询数据
     *
     * @param model
     * @param pageNum
     * @param limit
     * @return
     */
    @GetMapping(value = "/carousel-list")
    public String toPagePicture_list(Model model,
                                     @RequestParam(value="pageNum", defaultValue = "1") int pageNum,
                                     @RequestParam(value = "limit",defaultValue = "10") int limit){

        Page<ContentVo> page = PageHelper.startPage(pageNum,limit);
        List<CarouselVo> list = carouselService.getListCarousel();

        model.addAttribute("picList",list);
        model.addAttribute("total",page.getTotal());
        return this.render("/admin/carousel-list");
    }

    @GetMapping(value = "/carousel-view")
    public String toPageCarousel_view(Model model,
                                      @RequestParam(value = "id",defaultValue = "") String id){
        CarouselVo carouselVo = carouselService.getCarouselById(id);
        model.addAttribute("Carousel",carouselVo);
        return this.render("/admin/carousel-view");
    }

    /**
     * 跳转到编辑页面 -根据id查询数据填充
     * @param model
     * @param id
     * @return
     */
    @GetMapping(value = "/carousel-edit")
    public String toPageCarousel_edit(Model model,
                                      @RequestParam(value = "id",defaultValue = "") String id){
        CarouselVo carouselVo = carouselService.getCarouselById(id);
        model.addAttribute("Carousel",carouselVo);
        return this.render("/admin/carousel-add");
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

        return this.render("/admin/carousel-list::list");
    }

    /**
     * 根据id更新数据
     * @param ids
     * @param type 1 状态status
     * @return
     */
    @PostMapping(value = "/carousel/updateCarouse")
    @ResponseBody
    public String updateCarouse(@RequestParam(value = "ids") String ids,
                                @RequestParam(value = "type") String type,
                                HttpServletRequest request){
        LOGGER.info("-------------------修改轮播图数据------------------");
        UserVo userVo = TaleUtils.getLoginUser(request);
        Boolean bool = carouselService.updateCarousel(ids,type,userVo);
        JSONObject jsonObject = new JSONObject();
        if(bool){
            jsonObject.put("code","100000");
            jsonObject.put("msg","更新成功");
        }else {
            jsonObject.put("code","111111");
            jsonObject.put("msg","更新失败，请刷新数据！");
        }
        return jsonObject.toString();
    }
}
