package com.lwp.blog.dao;

import com.lwp.blog.entity.Vo.ProductCategoryVo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/22/14:06
 * @Description:
 */
@Component
public interface ProductCategoryDao {
    /**
     * 新增商品类别
     */
    int insertProductCategory(ProductCategoryVo productCategoryVo);

    /**
     * 更新商品类别
     */
    int updateProductCategory(ProductCategoryVo productCategoryVo);

    /**
     * 查询未删除的商品类别
     */
    List<ProductCategoryVo> getProductCategoryListByNotDelete();

    /**
     * 修改商品类别状态 启用/不启用
     */
    int updateProductCategoryWithStatus(Map<String,Object> map);

    /**
     * 删除商品
     */
    int updateProductCategoryWithDelete(Map<String,Object> map);

    /**
     * 根据id 查找 商品类别
     * @param id
     * @return
     */
    ProductCategoryVo getProductCategoryById(String id);

    /**
     * 根据id获取 商品类别级别
     * @param id
     * @return
     */
    String getCategoryLevelById(String id);

    /**
     * 根据id 获取tName
     * @param id
     * @return
     */
    String getCategoryParentName(String id);

    /**
     * 查找是否有相同的名称
     * @return
     */
    int getCountByName(String name);
}
