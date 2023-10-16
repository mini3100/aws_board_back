package com.korit.board.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ReturnAop {

    @Pointcut("@annotation(com.korit.board.aop.annotation.ReturnAop)")
    private void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object target = proceedingJoinPoint.proceed();
        System.out.println(target);
        return target;
    }
}
