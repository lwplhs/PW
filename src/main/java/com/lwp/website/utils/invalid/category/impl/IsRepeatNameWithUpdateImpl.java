package com.lwp.website.utils.invalid.category.impl;

import com.lwp.website.service.ProductCategoryService;
import com.lwp.website.utils.StringUtil;
import com.lwp.website.utils.invalid.category.IsRepeatNmaeWithUpdate;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/27/11:17
 * @Description:
 */
public class IsRepeatNameWithUpdateImpl implements ConstraintValidator<IsRepeatNmaeWithUpdate,Object> {

    private String vId;

    private String vName;

    @Resource
    private ProductCategoryService productCategoryService;

    @Override
    public void initialize(IsRepeatNmaeWithUpdate constraintAnnotation) {
        this.vId = constraintAnnotation.vId();
        this.vName = constraintAnnotation.vName();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String message ="";
        if(StringUtil.isNull(o)){
            message = "发生错误，请刷新后重试";
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        BeanWrapper beanWrapper = new BeanWrapperImpl(o);
        Object id = beanWrapper.getPropertyValue(vId);
        Object name  = beanWrapper.getPropertyValue(vName);

        if(StringUtil.isNull(id) || StringUtil.isNull(name)){
            message = "商品类别id或者名称为空";
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        int num = productCategoryService.getCountProductCategoryByNameId(String.valueOf(id),String.valueOf(name));
        if(num > 0){
            return false;
        }
        return true;
    }
}
