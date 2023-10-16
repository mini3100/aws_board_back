package com.korit.board.aop;

import com.korit.board.exception.ValidException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.HashMap;
import java.util.Map;

/*
* AOP는 필터와 같은 역할을 한다.
*/

@Aspect
@Component
public class ValidAop {

//    @Pointcut("execution(* com.korit.board.controller.*.*(..))")
    @Pointcut("@annotation(com.korit.board.aop.annotation.ValidAop)")
    private void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        BeanPropertyBindingResult bindingResult = null;

        Object[] args = proceedingJoinPoint.getArgs();
        for(Object arg : args) {
            if (arg.getClass() == BeanPropertyBindingResult.class){
                bindingResult = (BeanPropertyBindingResult) arg;
                break;
            }
        }

        if(bindingResult == null) {
            return proceedingJoinPoint.proceed();
        }

        if(bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            bindingResult.getFieldErrors().forEach(fieldError -> {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            });
            throw new ValidException(errorMap);
        }
        // .proceed() 메소드가 호출 되기 전 : 전처리
        Object target = proceedingJoinPoint.proceed();  // 메소드의 body 실행. target: 메소드의 return 값
        // .proceed() 메소드가 리턴 된 후 : 후처리

        return target;
    }
}
