package com.bwmanager.jaegwan.global.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 메서드 호출 전 로그 작성
    @Before("execution(* com.bwmanager.jaegwan..service..*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        logger.info("메서드 호출: {}.{}() with arguments: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());
    }

    // 메서드가 정상적으로 종료된 후 로그 작성
    @AfterReturning(pointcut = "execution(* com.bwmanager.jaegwan..service..*(..))", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        logger.info("메서드 종료: {}.{}() with result: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                result);
    }

    // 메서드가 예외로 종료된 후 로그 작성
    @AfterThrowing(pointcut = "execution(* com.bwmanager.jaegwan..service..*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logger.error("예외 발생: {}.{}() with error: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                error.getMessage());
    }
}
