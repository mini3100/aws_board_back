package com.korit.board.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class TimeAop {

    @Pointcut("@annotation(com.korit.board.aop.annotation.TimeAop)")
    private void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();  // 메소드 실행 전 시간 재기 시작
        Object target = proceedingJoinPoint.proceed();
        stopWatch.stop();   // 메소드 실행 전 시간 재기 종료
        System.out.println(stopWatch.getTotalTimeSeconds() + "초");  // 메소드 총 실행 시간
        return target;
    }
}
