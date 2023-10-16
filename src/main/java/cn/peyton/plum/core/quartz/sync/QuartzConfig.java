package cn.peyton.plum.core.quartz.sync;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.Serializable;

/**
 * <h3>任务调度 {方法}</h3>
 * <pre>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2021/11/4 19:59
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
@Configuration
public class QuartzConfig implements Serializable {

    /**
     * 1. 创建 Job 对象
     *
     */
    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean() {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        //关联 Job 类
        factory.setJobClass(MyQuartz.class);
//        factory.setJobClass(StartDataConfig.class);
        return factory;
    }

    //================================= 第一种实现方式 ===================================
    /**
     * 2. 创建 简单 Trigger 对象
     */
//    @Bean
//    public SimpleTriggerFactoryBean simpleTriggerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean) {
//        SimpleTriggerFactoryBean factory = new SimpleTriggerFactoryBean();
//        //关联 JobDetail 对象
//        factory.setJobDetail(jobDetailFactoryBean.getObject());
//        //关联时间参数表示一个执行时间（毫秒）
//        factory.setRepeatInterval(2*1000);
//        //设置重复次数
//        factory.setRepeatCount(5);
//        return factory;
//    }

    /**
     * 3. 创建 Scheduler 对象
     */
//    @Bean
//    public SchedulerFactoryBean schedulerFactoryBean(SimpleTriggerFactoryBean simpleTriggerFactoryBean) {
//        SchedulerFactoryBean factory = new SchedulerFactoryBean();
//        //关联 trigger
//        factory.setTriggers(simpleTriggerFactoryBean.getObject());
//        //
//        return factory;
//    }


    //================================= 第二种实现方式 ===================================
    /**
     *  Cron Trigger 对象
     */
    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean){
        CronTriggerFactoryBean factory = new CronTriggerFactoryBean();
        //关联 JobDetail 对象
        factory.setJobDetail(jobDetailFactoryBean.getObject());
        //设置触发时间
        factory.setCronExpression("0 0/1 * * * ?");
        return factory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(
                CronTriggerFactoryBean cronTriggerFactoryBean) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        //关联 trigger
        factory.setTriggers(cronTriggerFactoryBean.getObject());
        return factory;
    }

}
