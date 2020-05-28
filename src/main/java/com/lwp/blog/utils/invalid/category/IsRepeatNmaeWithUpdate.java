package com.lwp.blog.utils.invalid.category;

import com.lwp.blog.utils.invalid.category.impl.IsRepeatNameWithUpdateImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/27/9:47
 * @Description:
 */
@Documented
@Constraint(validatedBy = {IsRepeatNameWithUpdateImpl.class})
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(IsRepeatNmaeWithUpdate.list.class)
public @interface IsRepeatNmaeWithUpdate {
    String vId() default "0";

    String vName() default "0";

    String message() default "分类名称已存在";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface list{
        IsRepeatNmaeWithUpdate[] value();
    }
}
