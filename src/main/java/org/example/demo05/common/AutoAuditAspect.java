package org.example.demo05.common;

import org.example.demo05.utils.AuditEntity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Aspect
public class AutoAuditAspect {
    @Pointcut("execution(* org.example.demo05.service.implement.*.*add*(..))" +
            " || execution(* org.example.demo05.service.implement.*.*update*(..))" +
            "|| execution(* org.example.demo05.service.implement.*.*delete*(..))")
    public void pc() {
        //pass
    }

    @Around("pc()")
    public Object autoAudit(ProceedingJoinPoint p) throws Throwable {
        String method = p.getSignature().getName();//目标方法名
        Object[] args = p.getArgs();
        System.out.println("=========test==============");
        System.out.println(method);
        System.out.println("=========test==============");
        if (args.length > 0) {
            Object arg0 = args[0];
            System.out.println(arg0.toString());
            System.out.println("=========test==============");
            if (arg0 instanceof AuditEntity ae) {
                ae.setUpdateBy(Global.currentUser());
                ae.setUpdateDate(LocalDateTime.now());
                System.out.println(ae.getUpdateBy());
                System.out.println("=========test==============");
                if (method.contains("add")) {
                    ae.setRegisterBy(Global.currentUser());
                    ae.setRegisterDate(LocalDateTime.now());
                }
            }
        }
        //调用目标方法
        return p.proceed();
    }

}
