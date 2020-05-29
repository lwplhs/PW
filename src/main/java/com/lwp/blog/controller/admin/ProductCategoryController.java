package com.lwp.blog.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.lwp.blog.config.SysConfig;
import com.lwp.blog.controller.BaseController;
import com.lwp.blog.dao.ProductCategoryDao;
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
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
        return this.render("/admin/product/category/manage");
    }

    @PostMapping(value ="/getData")
    @ResponseBody
    public String getData(){
        List temp = productCategoryService.getProductCategoryList();
        String s = temp.toString();
        return s;
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
        String msg = "";
        if(bindingResult.hasErrors()){
            for(ObjectError error : bindingResult.getAllErrors()) {
                LOGGER.error(error.getDefaultMessage());
            }
            msg = (bindingResult.getAllErrors().get(0)).getDefaultMessage();
            jsonObject.put("code","111111");
            jsonObject.put("msg",msg);;
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
                                      @Validated(CategoryValidationGroups.GroupCategoryEdit.class) @ModelAttribute ProductCategoryVo productCategoryVo,
                                      BindingResult bindingResult){
        JSONObject jsonObject = new JSONObject();
        String msg = "";
        if(bindingResult.hasErrors()){
            for(ObjectError error : bindingResult.getAllErrors()) {
                LOGGER.error(error.getDefaultMessage());
            }
            msg = (bindingResult.getAllErrors().get(0)).getDefaultMessage();
            jsonObject.put("code","111111");
            jsonObject.put("msg",msg);
            return jsonObject.toString();
        }
        LOGGER.info("-------------------参数校验成功------------------");
        LOGGER.info("-------------------开始保存商品类别------------------");
        UserVo userVo = TaleUtils.getLoginUser(request);
        String result = productCategoryService.saveProductCategory(productCategoryVo,userVo);
        LOGGER.info("-------------------结束保存商品类别------------------");
        return result;
    }

    /**
     * 根据id更新 状态 1 启用 ，停用 2 删除
     * @param type
     * @param id
     * @return
     */
    @PostMapping(value = "updateProductCategoryStatusById")
    @ResponseBody
    public String updateProductCategoryStatusById(@RequestParam(value = "type") String type,
                                                  @RequestParam(value = "id") String id,
                                                  HttpServletRequest request){
        LOGGER.info("-------------------修改商品分类状态------------------");
        UserVo userVo = TaleUtils.getLoginUser(request);
        Boolean bool = productCategoryService.updateProductCategoryWithType(type,id,userVo);
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


    @PostMapping(value = "updateNameById")
    @ResponseBody
    public String updateProductCategoryNameById(@RequestParam(value = "id") @NotBlank(message = "请刷新页面重试") String id,
                                                @RequestParam(value = "name") @NotBlank(message = "名称不能为空") String name,
                                                HttpServletRequest request){
        UserVo userVo = TaleUtils.getLoginUser(request);
        Boolean bool = productCategoryService.updateProductCategoryNameById(id,name,userVo);

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
