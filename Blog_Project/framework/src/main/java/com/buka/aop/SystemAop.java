package com.buka.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class SystemAop {
    @Pointcut("@annotation(SystemLog)")
    public void pointCut(){
    }
    @Around("pointCut()")
    public Object aRound(ProceedingJoinPoint joinPoint){
        try {
            before(joinPoint);
            Object proceed = joinPoint.proceed(joinPoint.getArgs());
            after(joinPoint,proceed);
            return proceed;
        } catch (Throwable e) {
            exception(joinPoint,e);
            throw new RuntimeException(e);
        }
    }

    private void before(ProceedingJoinPoint joinPoint){
        log.info("================Start=================");
        try {
            SystemLog systemLog = joinPoint.getSignature().getDeclaringType().getMethod(joinPoint.getSignature().getName()).getAnnotation(SystemLog.class);
            log.info("============"+systemLog.name()+"============");
            log.info("参数："+ JSON.toJSONString(joinPoint.getArgs()));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
    public void after(ProceedingJoinPoint joinPoint,Object o){
        log.info("结果："+JSON.toJSONString(o));
        log.info("==========End=========");
    }
    public void exception(ProceedingJoinPoint joinPoint,Throwable e){
        log.error("报错："+joinPoint.getSignature().getDeclaringType()+"异常："+e.getMessage());
    }
}
