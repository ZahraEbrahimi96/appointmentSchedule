package com.learning.appointmentschedule.config;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExceptionHandler {
//
//
//    //    @Around("@annotation(ExceptionHandlerAndLogger)")
//    @Around("execution(* com.learning.appointmentschedule.controller..*.*(..))")
//    public ResponseEntity around(ProceedingJoinPoint joinPoint) throws Throwable {
//        try {
//            System.out.println("Starting run method "+joinPoint.getSignature().getName());
//            System.out.println("Parameter: "+joinPoint.getArgs()[0]);
//
//            Object result = joinPoint.proceed();   // اجرای متد
//            System.out.println("Info : " + result);
////            log.info("Info : " + joinPoint.getSignature().getName());
//
//            System.out.println("Starting executed "+joinPoint.getSignature().getName());
//            System.out.println("Output : "+result);
//            return ResponseEntity.ok(result);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
////            log.error(e.getMessage());
//            return ResponseEntity.status(500).body(ExceptionWrapper.getMessage(e));
//        }
//    }
//
////
////    @After("execution(* com.learning.appointmentschedule..*.*(..))")
////    public void drawLine() {
////        System.out.println("--------------------------------------------");
////    }
}
