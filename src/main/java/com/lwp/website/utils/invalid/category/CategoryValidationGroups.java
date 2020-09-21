package com.lwp.website.utils.invalid.category;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/25/15:27
 * @Description:
 */
public class CategoryValidationGroups {

    /**
     * 通用分组
     */
    public interface GroupCategoryCommon{}

    /**
     * 修改分组
     */
    public interface GroupCategoryEdit extends GroupCategoryCommon{}

    /**
     * 新增分组
     */
    public interface GroupCategoryAdd extends GroupCategoryCommon{}

}
