package com.lwp.blog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lwp.blog.dao.AttachmentDao;
import com.lwp.blog.dao.ProductCategoryDao;
import com.lwp.blog.entity.Vo.ProductCategoryVo;
import com.lwp.blog.entity.Vo.UserVo;
import com.lwp.blog.service.ProductCategoryService;
import com.lwp.blog.utils.*;
import com.lwp.blog.utils.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/22/17:04
 * @Description:
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);

    @Resource
    private ProductCategoryDao productCategoryDao;

    @Resource
    private AttachmentDao attachmentDao;

    @Override
    public String saveProductCategory(ProductCategoryVo productCategoryVo, UserVo userVo) {

        JSONObject jsonObject = new JSONObject();
        int num = 0;
        try {
            if (StringUtil.isNull(productCategoryVo.getId())) {
                String id = UUID.createID();
                productCategoryVo.setId(id);
                productCategoryVo.setCreateTime(new Date());
                productCategoryVo.setUpdateTime(new Date());
                productCategoryVo.setIsDelete("0");
                productCategoryVo.setCreateUserId(userVo.getUid().toString());
                productCategoryVo.setUpdateUserId(userVo.getUid().toString());
                num = productCategoryDao.insertProductCategory(productCategoryVo);
                if (num > 0) {
                    jsonObject.put("code", "100000");
                    jsonObject.put("msg", "添加成功");
                } else {
                    jsonObject.put("code", "100001");
                    jsonObject.put("msg", "添加失败");
                }
            } else {
                productCategoryVo.setUpdateTime(new Date());
                productCategoryVo.setUpdateUserId(userVo.getUid().toString());
                num = productCategoryDao.updateProductCategory(productCategoryVo);
                if (num > 0) {
                    jsonObject.put("code", "100000");
                    jsonObject.put("msg", "修改成功");
                } else {
                    jsonObject.put("code", "100002");
                    jsonObject.put("msg", "修改失败");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("code","100003");
            jsonObject.put("msg","出现错误，请刷新页面重试");
        }

        return jsonObject.toString();
    }

    @Override
    public String updateProductCategory(ProductCategoryVo productCategoryVo) {
        return null;
    }

    /**
     * 获取商品类别
     * @return
     */
    @Override
    public List getProductCategoryList() {
        LOGGER.info("-----------------------------获取商品分类");
        List<ProductCategoryVo> list = productCategoryDao.getProductCategoryListByNotDelete();
        List temp = new ArrayList();
        for(int i = 0; i < list.size();i++){
            ProductCategoryVo productCategoryVo = (ProductCategoryVo) list.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",productCategoryVo.getId());
            jsonObject.put("pId",productCategoryVo.getParentId());
            jsonObject.put("name",productCategoryVo.getName());
            jsonObject.put("status",productCategoryVo.getStatus());
            temp.add(jsonObject);
        }
        LOGGER.info(temp.toString());
        LOGGER.info("-----------------------------获取商品分类结束");
        return temp;
    }

    @Override
    public String updateProductCategoryWithType(String type) {
        return null;
    }

    /**
     * 根据id查找商品分类
     * @param id
     * @param type 1 新增 2 修改
     * @return
     */
    @Override
    public ProductCategoryVo getProductCategoryById(String id,String type) {
        ProductCategoryVo productCategoryVo = new ProductCategoryVo();
        switch (type){
            case "1":
                if(StringUtil.isNull(id) || "0".equals(id)){
                    productCategoryVo.setLevel("1");
                    productCategoryVo.setParentId("0");
                }else{
                    productCategoryVo.setParentId(id);
                    String level = this.getLevelById(id);
                    level = String.valueOf(Integer.parseInt(level)+1);
                    productCategoryVo.setLevel(level);
                }
                break;
            case "2":
                if(!StringUtil.isNull(id) && !"0".equals(id)){
                    productCategoryVo = productCategoryDao.getProductCategoryById(id);
                }
                break;
            default:
                break;
        }
        return productCategoryVo;
    }

    /**
     * 根据parentId获取parentName
     * @param parentId
     * @return
     */
    @Override
    public String getCategoryParentName(String parentId) {

        String parentName = "";
        if(StringUtil.isNull(parentId) || "0".equals(parentId)){
            parentName = "顶级类别";
        }else {
            String temp = productCategoryDao.getCategoryParentName(parentId);
            if(StringUtil.isNull(temp)){
                parentName = "顶级类别";
            }else {
                parentName = temp;
            }
        }
        return parentName;
    }

    /**
     * 根据附件id获取附件路径
     * @return
     */
    @Override
    public String getAttachmentPathById(String aId) {
        String path = "";
        if(!StringUtil.isNull(aId)) {
            try {
                path =  attachmentDao.getPathById(aId);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return path;
    }

    /**
     * 获取当前id的级别
     */
    private String getLevelById(String id){
        String level = "1";
        if(StringUtil.isNull(id) || "0".equals(id)){
            return level;
        }
        String temp = productCategoryDao.getCategoryLevelById(id);
        if(!StringUtil.isNull(temp) && Tools.isNumber(temp)){
            level = temp;
        }
        return level;
    }
}
