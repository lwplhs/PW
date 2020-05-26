package com.lwp.blog.utils.invalid.carousel.impl;

import com.lwp.blog.utils.StringUtil;
import com.lwp.blog.utils.Tools;
import com.lwp.blog.utils.invalid.carousel.IsNum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/09/11:07
 * @Description:
 */
public class IsNumImpl implements ConstraintValidator<IsNum, String> {

    @Override
    public void initialize(IsNum constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(StringUtil.isNull(s)){
            return true;
        }else {
            if(Tools.isNumber(s)){
                return true;
            }
        }
        return false;
    }

}
