//package org.example.demo05.common;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.springframework.stereotype.Component;
//
//@Component
//@Aspect
//public class StatExecuteTimeAspect {
//    //定义切点
//    @Pointcut("execution(* org.example.demo05.service.implement.*.*(..))")
//    public void pc() {
//        //pass
//    }
//
//    //前置通知
//    @Before("pc()")
//    public void advice1() {
//        IO.println("你被在前面切了");
//    }
//
//    //后置通知
//    @After("pc()")
//    public void advice2() {
//        IO.println("你被在后面切了");
//    }
//
//    //环绕通知
//    @Around("pc()")
//    public Object advice3(ProceedingJoinPoint p) throws Throwable {
//        String method = p.getSignature().getName();//目标方法名
//
//        IO.println("环绕切入1111");
//        long start = System.currentTimeMillis();
//        //调用目标方法
//        Object result = p.proceed();
//        IO.println("环绕切入2222");
//        long end = System.currentTimeMillis();
//
//        IO.println("方法" + method + "共执行" + (end - start) + "毫秒");
//
//        return result;
//    }
//
//    //返回值后通知
//    @AfterReturning
//    public void advice3() {
//        IO.println("返回值后通知");
//    }
//
//    //异常后通知
//    @AfterThrowing
//    public void advice4() {
//        IO.println("异常后通知");
//    }
//
//
//}
