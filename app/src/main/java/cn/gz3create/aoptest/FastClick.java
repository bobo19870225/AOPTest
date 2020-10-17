package cn.gz3create.aoptest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在需要定制时间间隔地方添加@FastClick注解
 * 如果使用了混淆，注意被切点注解标识的类与方法一定要keep住
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FastClick {
    long value() default FastClickAspect.FAST_CLICK_INTERVAL_GLOBAL;
}
