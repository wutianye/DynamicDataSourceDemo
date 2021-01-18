package com.example.demo.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.core.annotation.AnnotationUtils;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

@Component
@Aspect
@Order(-1)
public class DataSourceAspect {
 

    @Pointcut("@annotation(com.example.demo.config.DataSource)")
    public void pointCut() {
    }
    @Around("pointCut()")
    public Object doBefore(ProceedingJoinPoint joinPoint) throws Throwable {
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//         Method method = signature.getMethod();
//        DataSource dataSource = AnnotationUtils.findAnnotation(method, DataSource.class);
//        DataSourceKeyEnum keyEnum = DataSourceKeyEnum.getDataSourceKey(dataSource);
      
     
      // 获取类级别注解
      DataSource classAnnotation = joinPoint.getTarget().getClass().getAnnotation(DataSource.class);
      if (classAnnotation == null){  
          MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
          Method method = methodSignature.getMethod();
          classAnnotation = method.getAnnotation(DataSource.class);
          if (classAnnotation == null){
          //获取接口上的注解
            for (Class<?> cls : joinPoint.getClass().getInterfaces()) {
              classAnnotation = cls.getAnnotation(DataSource.class);
                if (classAnnotation != null) {
                    break;
                }
            }
          }
      }
      DataSourceKeyEnum keyEnum = DataSourceKeyEnum.getDataSourceKey(classAnnotation);
         System.out.println("选择的数据源："+keyEnum.getValue());
         DataSourceContextHolder.setDataSource(keyEnum.getValue());
         Object o=joinPoint.proceed();
         DataSourceContextHolder.clear();
         return o;
    }
    @Pointcut("execution(* com.baomidou.mybatisplus.extension.service.IService.*Batch*(..)))")
    public void pointCutBatch() {
    }
    //对mybatisplus批量操作切面
    @Around("pointCutBatch()")
    public Object doBeforeBatch(ProceedingJoinPoint pjp) throws Throwable {
        DataSourceContextHolder.setDataSource(DataSourceKeyEnum.MASTER.getValue());
        Object o = pjp.proceed();
        DataSourceContextHolder.clear();
        return o;
    }
}
