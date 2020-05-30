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
                if(StringUtil.isNull(productCategoryVo.getLevel())){
                    //获取当前层级
                    String parentId = productCategoryVo.getParentId();
                    if(!StringUtil.isNull(parentId)){
                        String level = this.getLevelById(parentId);
                        productCategoryVo.setLevel(String.valueOf(Integer.parseInt(level)+1));
                    }
                }
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
                String level = productCategoryVo.getLevel();
                String id = productCategoryVo.getId();
                if(StringUtil.isNull(level) && !StringUtil.isNull(id)){
                    level = this.getLevelById(id);
                    productCategoryVo.setLevel(level);
                }
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
     * 判断该节点是否有子节点
     */
    private Boolean isLeaf(String id){
        List<ProductCategoryVo> list = productCategoryDao.getProductCategoryListByParentId(id);
        if(StringUtil.isNull(list)){
            return true;
        }
        return false;

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
            String id = productCategoryVo.getId();
            jsonObject.put("id",id);
            jsonObject.put("pId",productCategoryVo.getParentId());
            jsonObject.put("name",productCategoryVo.getName());
            jsonObject.put("status",productCategoryVo.getStatus());
            /**
             * 判断是否允许拖拽
             */
            if(this.isLeaf(id)){
                jsonObject.put("drag",true);
            }else {
                jsonObject.put("drag",false);
            }
            /**
             * 判断是否允许拖入
             */
            String level = this.getLevelById(id);
            if("1".equals(level)){
                jsonObject.put("drop",true);
            }else {
                jsonObject.put("drop",false);
            }

            temp.add(jsonObject);
        }
        LOGGER.info(temp.toString());
        LOGGER.info("-----------------------------获取商品分类结束");
        return temp;
    }

    /**
     * 根据id，type 修改 商品分类的状态
     * @param type 1 启用/停用 2 删除
     * @param id
     * @return
     */
    @Override
    public Boolean updateProductCategoryWithType(String type,String id,UserVo userVo) {
        Boolean bool = false;
        if(type != null){
            switch (type){
                case "1":
                    bool = this.updateStatus(id,userVo);
                    break;
                case "2":
                    bool = this.updateDelete(id,userVo);
                    break;
                default:
                    break;
            }
        }

        return bool;
    }


    /**
     * 修改状态 启用/未启用 将该分类目录下的状态统一
     * @param ids
     * @param userVo
     * @return
     */
    private Boolean updateStatus(String ids,UserVo userVo){
        List<String> temp = this.toList(ids);
        if(!StringUtil.isNull(temp)){
            Map<String,Object> map = new HashMap<>();
            map.put("ids",temp);
            map.put("updateTime",new Date());
            map.put("updateUserId",userVo.getUid());
            int count = productCategoryDao.updateProductCategoryWithStatus(map);
            if(count > 0){
                for(int i = 0; i < temp.size();i++){
                    String id = temp.get(i);
                    this.updateLeaf(id,"1");
                }
                return true;
            }
        }
        return false;
    }


    /**
     * 根据父id 修改下面的状态
     * @param id
     * @param type 1 状态  2 删除
     */
    private void updateLeaf(String id,String type){
        ProductCategoryVo vo = productCategoryDao.getProductCategoryById(id);
        switch (type){
            case "1":

                String status = vo.getStatus();
                List<ProductCategoryVo> list = productCategoryDao.getProductCategoryListByParentId(id);
                if(!StringUtil.isNull(list)){
                    for(int i = 0;i < list.size();i++){
                        ProductCategoryVo productCategoryVo = list.get(i);
                        String subId = productCategoryVo.getId();
                        productCategoryDao.updateProductCategoryWithStatusById(subId,status);
                        this.updateLeaf(subId,"1");
                    }
                }
                break;
            case "2":
                String isDelete = vo.getIsDelete();
                List<ProductCategoryVo> list1 = productCategoryDao.getProductCategoryListByParentId(id);
                if(!StringUtil.isNull(list1)){
                    for(int i=0;i<list1.size();i++){
                        ProductCategoryVo productCategoryVo = list1.get(i);
                        String subId = productCategoryVo.getId();
                        productCategoryDao.updateProductCategoryWithDeleteById(subId,isDelete);
                        this.updateLeaf(subId,"2");
                    }
                }
                break;
        }
    }

    /**
     * 更新删除状态，将商品类别删除
     * @param ids
     * @return
     */
    private boolean updateDelete(String ids,UserVo userVo){
        List<String> temp = this.toList(ids);
        if(!StringUtil.isNull(temp)){
            Map<String,Object> map = new HashMap<>();
            map.put("ids",temp);
            map.put("updateTime",new Date());
            map.put("updateUserId",userVo.getUid());
            int count = productCategoryDao.updateProductCategoryWithDelete(map);
            if(count > 0){
                for(int i = 0; i < temp.size();i++){
                    String id = temp.get(i);
                    this.updateLeaf(id,"2");
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 将 ids 转化成list
     * @param ids
     * @return
     */
    private List<String> toList(String ids){
        List<String> list = new ArrayList();
        String[] args = ids.split(",");
        if(null != args && args.length > 0){
            for(int i = 0;i < args.length;i++){
                list.add(args[i]);
            }
        }
        return list;
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
     * 根据 id 更新类型名称
     * @param id
     * @param name
     * @param userVo
     * @return
     */
    @Override
    public Boolean updateProductCategoryNameById(String id, String name, UserVo userVo) {
        String updateUserId = userVo.getUid().toString();
        Date date = new Date();
        int num = productCategoryDao.updateCategoryNameById(name,id,updateUserId,date);

        return num > 0?true:false;
    }


    /**
     * 获取类型名称是否被占用
     * @param id
     * @param name
     * @return
     */
    @Override
    public int getCountProductCategoryByNameId(String id, String name) {
        int num = productCategoryDao.getCountByName(name,id);
        return num;
    }

    @Override
    public int updateProductCategoryDrag(String dragId, String dropId, UserVo userVo) {

        int num = 0;
        ProductCategoryVo productCategoryVo = productCategoryDao.getProductCategoryById(dragId);
        if(StringUtil.isNull(productCategoryVo)){
            num = 0;
        }else {
            String level = this.getLevelById(dropId);
            productCategoryVo.setLevel(String.valueOf(Integer.parseInt(level)+1));
            productCategoryVo.setParentId(dropId);
            num = productCategoryDao.updateProductCategory(productCategoryVo);
        }

        return num;
    }

    /**
     * 获取当前id的级别
     */
    private String getLevelById(String id){
        String level = "0";
        if(StringUtil.isNull(id) || "0".equals(id)){
            return level;
        }
        String temp = productCategoryDao.getCategoryLevelById(id);
        if(!StringUtil.isNull(temp) && Tools.isNumber(temp)){
            level = temp;
            return level;
        }else{
            int t = 1;
            ProductCategoryVo productCategoryVo = productCategoryDao.getProductCategoryById(id);
            level = String.valueOf(Integer.parseInt(this.getLevelById(productCategoryVo.getParentId()))+1);
            return level;
        }

    }
}
