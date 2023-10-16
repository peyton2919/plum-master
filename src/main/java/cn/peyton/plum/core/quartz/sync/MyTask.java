package cn.peyton.plum.core.quartz.sync;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * <h3>第二种定时器</h3>
 * <pre>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2021/11/7 23:09
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
@Configuration      //1. 标记配置类，使得 springboot 容器可以扫描到
@EnableScheduling   //2. 开启定时任务
@Slf4j
public class MyTask {

    //3. 添加一个任务，并且注明任务的运行表达式
    @Scheduled(cron = "0 0/3 * * * ?")
    public void publishMsg() {
        System.out.println("同步方法：MyTask -- publishMsg:" + LocalDateTime.now());

        log.warn("开始执行任务：-- {}", LocalDateTime.now());
    }
}
