package cn.gz3create.aoptest;

import android.util.Log;
import android.view.View;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author chenli
 * @create 2020/9/8
 * @Describe
 */
@Aspect
public class FastClickAspect {

    public static final long FAST_CLICK_INTERVAL_GLOBAL = 800L;

    @Pointcut("execution(void android.view.View.OnClickListener.onClick(..))")
    public void methodViewOnClick() {
        Log.e("methodViewOnClick", "methodViewOnClick: ");
    }

    @Around("methodViewOnClick()")
    public void aroundViewOnClick(ProceedingJoinPoint joinPoint) throws Throwable {
        // 取出JoinPoint的签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 取出JoinPoint的方法
        Method method = methodSignature.getMethod();

        // 1. 全局统一的时间间隔
        long interval = FAST_CLICK_INTERVAL_GLOBAL;

        if (method != null && method.isAnnotationPresent(FastClick.class)) {
            // 2. 如果方法使用了@FastClick修饰，取出定制的时间间隔

            FastClick singleClick = method.getAnnotation(FastClick.class);
            interval = singleClick.value();
        }
        // 取出目标对象
        View target = (View) joinPoint.getArgs()[0];
        // 3. 根据点击间隔是否超过interval，判断是否为快速点击
        // 根据点击间隔是否超过FAST_CLICK_INTERVAL_GLOBAL，判断是否为快速点击
        if (!FastClickCheckUtil.isFastClick(target, interval)) {
            joinPoint.proceed();
        }
    }
}
