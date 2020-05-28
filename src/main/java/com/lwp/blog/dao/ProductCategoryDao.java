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
     * 根据 id 修改状态 type 1启用 0 不启用
     * @param id
     * @param status
     * @return
     */
    int updateProductCategoryWithStatusById(String id,String status);

    /**
     * 根据 id 进行删除  isDelete 0 不删除 1 删除
     * @return
     */
    int updateProductCategoryWithDeleteById(String id,String isDelete);

    /**
     * 根据父id 获取所有的叶子节点
     * @param parentId
     * @return
     */
    List<ProductCategoryVo> getProductCategoryListByParentId(String parentId);

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
     * 查找是否有相同的名称 id 不为空 则查找排除该id的name
     * @return
     */
    int getCountByName(String name,String id);


}
