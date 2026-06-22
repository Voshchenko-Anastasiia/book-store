package com.epam.rd.autocode.spring.project.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
// requirement: AOP (Aspect-Oriented Programming) for implementing logging
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.epam.rd.autocode.spring.project.service..*.*(..))")
    public void serviceMethods() {}

    @Around("serviceMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        long startTime = System.currentTimeMillis();

        try {
            logger.debug("Entering: {}", methodName);

            Object result = joinPoint.proceed();

            long duration = System.currentTimeMillis() - startTime;
            logger.info("Method {} executed successfully in {} ms", methodName, duration);

            return result;
        } catch (Throwable e) {
            logger.error("Exception in method {}! Message: {}", methodName, e.getMessage());
            throw e; // re-throw the exception so the app handles it correctly
        }
    }
}
