package ru.example.micro.parking.config;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

/**
 * @author Tarkhov Evgeniy
 */
@Log4j2
@Aspect
@Component
public class LoggerAspect {

    @Pointcut("execution(* ru.example.micro.parking..*(..))")
    public void allMethods() { }

    @Before("allMethods()")
    public void before(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        String className = jp.getSignature().getDeclaringTypeName();
        String message = String.format("Before: %s.%s, args: %s", className, methodName, getParameters(jp));
        log.debug(message);
    }

    @AfterReturning(pointcut = "allMethods()", returning = "result")
    public void afterReturn(JoinPoint jp, Object result) {
        var methodName = jp.getSignature().getName();
        var className = jp.getSignature().getDeclaringTypeName();
        var resultStr = nonNull(result) ? result.toString() : null;
        var message = String.format("After: %s.%s, Result: %s", className, methodName, resultStr);
        log.debug(message);
    }

    @AfterThrowing(value = "allMethods()", throwing = "exception")
    public void afterThrowing(JoinPoint jp, Throwable exception) {
        var methodName = jp.getSignature().getName();
        var className = jp.getSignature().getDeclaringTypeName();
        var exMsg = String.format("%s - %s", exception.getClass().getSimpleName(), exception.getMessage());
        var message = String.format("Throws exception: %s.%s, Exception: %s", className, methodName, exMsg);
        log.error(message);
    }

    private Map<String, Object> getParameters(JoinPoint joinPoint) {
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();
        Map<String, Object> map = new HashMap<>();
        String[] parameterNames = signature.getParameterNames();
        for (int i = 0; i < parameterNames.length; i++) {
            map.put(parameterNames[i], joinPoint.getArgs()[i]);
        }
        return map;
    }
}
