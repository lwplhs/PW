package com.lwp.website.utils.invalid.category;

import com.lwp.website.utils.invalid.category.impl.IsRepeatNameImpl;

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
//出现的位置 FIELD 属性 METHOD方法 TYPE 类
@Target({ElementType.METHOD,ElementType.FIELD})
//生命周期 runtime 运行时 class class文件SOURCE 源码
@Retention(RetentionPolicy.RUNTIME)
public @interface IsRepeatName {
    String message() default "商品类别名称已存在";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default {};
}
