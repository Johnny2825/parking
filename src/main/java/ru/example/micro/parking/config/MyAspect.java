package ru.example.micro.parking.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Tarkhov Evgeniy
 */
//@Log4j2
@Aspect
@Component
public class MyAspect {
    private static final Logger log = LoggerFactory.getLogger(MyAspect.class);


    @Pointcut("execution(public * ru.example.micro.parking.service.business.user.*.*(..))")
    public void callAtMyServicePublic() { }

    @Before("callAtMyServicePublic()")
    public void before(JoinPoint jp) {
        System.out.println("Before");
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp + ", args=[" + args + "]");
    }

    @AfterReturning("callAtMyServicePublic()")
    public void afterReturn(JoinPoint jp) {
        System.out.println("After");
        log.info("after return: " + Arrays.toString(jp.getArgs()));
    }

    @AfterThrowing("callAtMyServicePublic()")
    public void afterThrowing(JoinPoint jp) {
        log.info("after throwing: " + Arrays.toString(jp.getArgs()));
    }
}
