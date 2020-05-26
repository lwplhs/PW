package com.lwp.blog.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.lwp.blog.controller.BaseController;
import com.lwp.blog.entity.Vo.ProductCategoryVo;
import com.lwp.blog.entity.Vo.UserVo;
import com.lwp.blog.service.ProductCategoryService;
import com.lwp.blog.utils.StringUtil;
import com.lwp.blog.utils.TaleUtils;
import com.lwp.blog.utils.invalid.category.CategoryValidationGroups;
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
 * @Date: 2020/05/22/17:05
 * @Description:
 */
@Controller("productCategoryController")
@RequestMapping(value = "/admin/productCategory")
public class ProductCategoryController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCategoryController.class);

    @Resource
    private ProductCategoryService productCategoryService;

    @GetMapping(value = "/manage")
    public String toPageProductCategoryManage(Model model){

        List temp = productCategoryService.getProductCategoryList();

        model.addAttribute("categoryList",temp);

        return this.render("/admin/product/category/manage");
    }

    /**
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/productCategory-add")
    public String toPageProductCategoryAdd(Model model,
                                            HttpServletRequest request,
                                            HttpServletResponse response,
                                           @RequestParam(value = "parentId",defaultValue = "0") String parentId){
        String parentName="";
        ProductCategoryVo productCategoryVo = new ProductCategoryVo();
        productCategoryVo.setParentId(parentId);
        parentName = productCategoryService.getCategoryParentName(parentId);
        model.addAttribute("category",productCategoryVo);
        model.addAttribute("parentName",parentName);

        return this.render("/admin/product/category/add");
    }

    /**
     *
     * @param model
     * @param request
     * @param response
     * @param id id
     * @return
     */
    @GetMapping(value = "/productCategory-edit")
    public String toPageProductCategoryEdit(Model model,
                                            HttpServletRequest request,
                                            HttpServletResponse response,
                                            @RequestParam(value = "id",defaultValue = "0") String id){
        String parentName="";
        String path="";
        ProductCategoryVo productCategoryVo = productCategoryService.getProductCategoryById(id,"2");
        if(!StringUtil.isNull(productCategoryVo)){
            String parentId = productCategoryVo.getParentId();
            parentName = productCategoryService.getCategoryParentName(parentId);
            String aid = productCategoryVo.getAttachmentId();
            path = productCategoryService.getAttachmentPathById(aid);
        }
        model.addAttribute("category",productCategoryVo);
        model.addAttribute("parentName",parentName);
        model.addAttribute("path",path);

        return this.render("/admin/product/category/edit");
    }

    /**
     *
     * @param model
     * @param request
     * @param response
     * @param id id
     * @return
     */
    @GetMapping(value = "/productCategory-view")
    public String toPageProductCategoryView(Model model,
                                            HttpServletRequest request,
                                            HttpServletResponse response,
                                            @RequestParam(value = "id",defaultValue = "0") String id){
        String parentName="";
        String path="";
        ProductCategoryVo productCategoryVo = productCategoryService.getProductCategoryById(id,"2");
        if(!StringUtil.isNull(productCategoryVo)){
            String parentId = productCategoryVo.getParentId();
            parentName = productCategoryService.getCategoryParentName(parentId);
            String aid = productCategoryVo.getAttachmentId();
            path = productCategoryService.getAttachmentPathById(aid);
        }
        model.addAttribute("category",productCategoryVo);
        model.addAttribute("parentName",parentName);
        model.addAttribute("path",path);

        return this.render("/admin/product/category/view");
    }


    @GetMapping(value = "/list")
    public String toPageList(){
        return this.render("/admin/product/category/list");
    }

    @PostMapping(value = "/saveCategory")
    @ResponseBody
    public String saveProductCategory(HttpServletRequest request,
                                      HttpServletResponse response,
                                      @Validated(CategoryValidationGroups.GroupCategoryAdd.class) @ModelAttribute ProductCategoryVo productCategoryVo,
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
        LOGGER.info("-------------------开始保存商品类别------------------");
        UserVo userVo = TaleUtils.getLoginUser(request);
        String result = productCategoryService.saveProductCategory(productCategoryVo,userVo);
        LOGGER.info("-------------------结束保存商品类别------------------");
        return result;
    }
    @PostMapping(value = "/updateCategory")
    @ResponseBody
    public String updateProductCategory(HttpServletRequest request,
                                      HttpServletResponse response,
                                      @Valid @ModelAttribute ProductCategoryVo productCategoryVo,
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
        LOGGER.info("-------------------开始保存商品类别------------------");
        UserVo userVo = TaleUtils.getLoginUser(request);
        String result = productCategoryService.saveProductCategory(productCategoryVo,userVo);
        LOGGER.info("-------------------结束保存商品类别------------------");
        return result;
    }

}
