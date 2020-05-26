package com.lwp.blog.utils.invalid.category.impl;

import com.lwp.blog.dao.ProductCategoryDao;
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
    private ProductCategoryDao productCategoryDao;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(!StringUtil.isNull(s)){
            int num = productCategoryDao.getCountByName(s);
            if(num > 0){
                return false;
            }
        }
        return true;
    }
}
