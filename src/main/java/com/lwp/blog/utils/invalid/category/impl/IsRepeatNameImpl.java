package com.lwp.blog.utils.invalid.category.impl;

import com.lwp.blog.dao.ProductCategoryDao;
import com.lwp.blog.service.ProductCategoryService;
import com.lwp.blog.utils.StringUtil;
import com.lwp.blog.utils.invalid.category.IsRepeatName;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/25/15:04
 * @Description:
 */

public class IsRepeatNameImpl implements ConstraintValidator<IsRepeatName,String> {
    @Override
    public void initialize(IsRepeatName constraintAnnotation) {

    }

    @Resource
    private ProductCategoryService productCategoryService;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(!StringUtil.isNull(s)){
            int num = productCategoryService.getCountProductCategoryByNameId(null,s);
            if(num > 0){
                return false;
            }
            return true;
        }else {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("商品分类名称不能为空").addConstraintViolation();
            return false;
        }

    }
}
