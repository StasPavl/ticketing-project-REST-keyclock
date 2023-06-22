package com.cydeo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    private String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SimpleKeycloakAccount userDetails = (SimpleKeycloakAccount) authentication.getDetails();
        return userDetails.getKeycloakSecurityContext().getToken().getPreferredUsername();
    }


//    Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    @Pointcut("execution(* com.cydeo.controller.ProjectController.*(..)) || execution(* com.cydeo.controller.TaskController.*(..))")
    public void anyProjectAndTaskController(){}

    @Before("anyProjectAndTaskController()")
    public void beforeAnyProjectAndTaskController(JoinPoint joinPoint){
        log.info("Before -> Method: {}, User: {}"
                , joinPoint.getSignature().toString()
                , getUsername());
    }

    @AfterReturning(pointcut = "anyProjectAndTaskController()", returning = "results")
    public void afterAnyProjectAndTaskController(JoinPoint joinPoint, Object results){
        log.info("Before -> Method: {}, User: {}, Results: {}"
                , joinPoint.getSignature().toString()
                , getUsername()
                , results.toString());
    }
    @AfterThrowing(pointcut = "anyProjectAndTaskController()", throwing = "exception")
    public void afterThrowingAnyProjectAndTaskController(JoinPoint joinPoint, Exception exception){
        log.info("Before -> Method: {}, User: {}, Results: {}"
                , joinPoint.getSignature().toString()
                , getUsername()
                , exception.getMessage());
    }


}
