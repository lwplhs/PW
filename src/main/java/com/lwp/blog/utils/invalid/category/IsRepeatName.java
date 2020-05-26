package com.lwp.blog.utils.invalid.category;

import com.lwp.blog.utils.invalid.category.impl.IsRepeatNameImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/25/15:03
 * @Description:
 */
@Documented
@Constraint(validatedBy = {IsRepeatNameImpl.class})
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsRepeatName {
    String message() default "商品类别名称已存在";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default {};
}
