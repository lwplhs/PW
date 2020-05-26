package com.lwp.blog.service;

import com.lwp.blog.entity.Vo.ProductCategoryVo;
import com.lwp.blog.entity.Vo.UserVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/22/16:44
 * @Description:
 */
public interface ProductCategoryService {

    /**
     * 新增商品类别
     */
    String saveProductCategory(ProductCategoryVo productCategoryVo, UserVo userVo);

    /**
     * 修改商品类别
     */

    String updateProductCategory(ProductCategoryVo productCategoryVo);

    /**
     * 获取商品类别
     */
    List getProductCategoryList();

    /**
     * 修改状态
     * type 1:是否启用 2：删除
     */
    String updateProductCategoryWithType(String type);


    /**
     * 新增/编辑/查看的时候获取商品类别
     * @param id
     * @param type 1 新增 2 修改
     * @return
     */
    ProductCategoryVo getProductCategoryById(String id,String type);

    /**
     * 根据父id获取父name
     * @param parentId
     * @return
     */
    String getCategoryParentName(String parentId);

    /**
     * 根据 附件id获取附件路径
     */
    String getAttachmentPathById(String aId);
}
