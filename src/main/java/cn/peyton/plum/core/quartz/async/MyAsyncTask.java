package cn.peyton.plum.core.quartz.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <h3>异步任务调度</h3>
 * <pre>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2021/11/7 23:15
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
@Component
@EnableAsync
@Slf4j
public class MyAsyncTask implements Serializable {


    @Async
    //@Scheduled(cron = "*/30 * * * * ?")
    @Scheduled(cron = "0 0/2 * * * ?")
    public void publishMsg() {
        System.out.println("异步方法：MyAsyncTask -- publishMsg:" + LocalDateTime.now());
        log.warn("开始执行任务：-- {}", LocalDateTime.now());
    }
}
