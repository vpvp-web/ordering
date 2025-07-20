package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.statement.select.Join;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    /**
     * 切入点
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointcut() {}


    @Before("autoFillPointcut()")
    public void autoFill(JoinPoint joinPoint) throws NoSuchMethodException {
        log.info("自动填充操作执行");

        // 获取方法上的数据库类型
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();  //方法签名签名
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class); //获取方法的注解对象
        OperationType operationType = autoFill.value();  //获得数据库操作类型

        //获得当前执行方法的参数-实体对象
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        Object entity = args[0];  //假设第一个参数是实体对象
        // 设置创建时间和更新时间
        LocalDateTime now = LocalDateTime.now();
        long currentId = BaseContext.getCurrentId();  //获取当前用户ID

        // 根据操作类型进行自动填充
        if (operationType == OperationType.INSERT) {
            try{
                //通过反射设置创建人、更新人、创建时间、更新时间
                entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class).invoke(entity, now);
                entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class).invoke(entity, now);
                entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class).invoke(entity,currentId);
                entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class).invoke(entity, currentId);
            } catch (Exception e) {
                log.error("自动填充失败: {}", e.getMessage());
            }
        }else if (operationType == OperationType.UPDATE) {
            try{
                //通过反射设置更新人、更新时间
                entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class).invoke(entity, now);
                entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, long.class).invoke(entity, currentId);
            } catch (Exception e) {
                log.error("自动填充失败: {}", e.getMessage());
            }
        }

    }
}
