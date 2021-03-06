package com.lwp.website.utils.invalid.carousel;

import com.lwp.website.utils.invalid.carousel.impl.IsNumImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/09/11:03
 * @Description:
 */
@Documented
@Constraint(validatedBy = {IsNumImpl.class})
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsNum {
    String message() default "必须是数字";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default {};
}
