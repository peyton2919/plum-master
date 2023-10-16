package cn.peyton.plum.core.aop.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * <h3>切片 AOP 的通知类型：</h3>
 * <h4>1. 前置通知 </h4>
 * <h4>2. 后置通知</h4>
 * <h4>3. 环练通知</h4>
 * <h4>4. 异常通知</h4>
 * <h4>5. 最终通知</h4>
 * <pre>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2018/11/16 15:44
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
@Aspect
@Slf4j
@Component
public class AopLogs implements Serializable {

    /**
     * <h4>监测service 包下执行时间 </h4>
     * @param point
     * @return
     * @throws Throwable
     */
    //@Around("execution(* cn.peyton.children.*.find*(..))")
    public Object printLogTimesOfService(ProceedingJoinPoint point) throws Throwable {
        ;
        log.info("正在执行{}.{}", point.getTarget().getClass(), point.getSignature().getName());
        long startTime = System.currentTimeMillis();
        Object result = point.proceed();
        long endTime = System.currentTimeMillis();
        long exTimes = endTime - startTime;
        if (exTimes > 3000) {
            log.error("当前执行耗时：{}",exTimes);
        }else if (exTimes > 200){
            log.warn("当前执行耗时：{}",exTimes);
        }else {
            log.info("当前执行耗时：{}",exTimes);
        }
        return result;
    }
}
