/**
 * <h3>定时器包</h3>
 * <pre>
 *     SpringBoot 整合 Quartz 定时任务：
 *     1. 添加jar：
 *          org.springframework.boot spring-boot-starter-quartz
*      2. Quartz 使用思路：
 *          1). Job --任务 --要做什么事？
 *          2). Trigger --触发器 -- 什么时候去做？
 *          3). Scheduler --任务调度 --什么时候需要去做什么事？
 *     3. Quartz 基本使用方式：
 *          1). 创建 Job 对象，要做什么事 JobDetail job = JobBuilder.newJob(MyQuartz.class).build();
 *          2). 简单 Trigger 触发时间：通过Quartz 提供一个方法来完成简单的重复调用 cron Trigger:
 *                  按照 Cron 的表达式来给定触发的时间,在withSchedule参数中调用
 *                  CronScheduleBuilder.cronSchedule("0/2 * * * * ?") Trigger trigger =
 *                      TriggerBuilder.newTrigger().withSchedule(SimpleScheduleBuilder.repeatSecondlyForever()).build;
*           3). 创建 Scheduler 对象,在什么时间做什么事？
 *              Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
 *              scheduler.scheduleJob(job,trigger);
 *          4). 启动 scheduler.start();
 *
 *     cronExpression表达式备忘：
 *          字段 允许值 允许的特殊字符 秒 0-59 – * / 分 0-59 – * / 小时 0-23 – * / 日期 1-31 – * ? / L W C 月份 1-12
 *          或者 JAN-DEC – * / 星期 1-7 或者 SUN-SAT – * ? / L C # 年 （可选）留空1970-2099 – * /
 *
 *          表达式意义：
 *          表达式 允许值 `0 0 12 * * ?` 每天中午12点触发
 *              `0 15 10 ? * *` 每天上午10:15触发
 *              `0 15 10 * * ?` 每天上午10:15触发
 *              `0 15 10 * * ? *` 每天上午10:15触发
 *              `0 15 10 * * ? 2005` 2005年的每天上午10:15触发
 *              `0 * 14 * * ?` 在每天下午2点到下午2:59期间的每1分钟触发
 *              `0 0/5 14 * * ?` 在每天下午2点到下午2:55期间的每5分钟触发
 *              `0 0/5 14,18 * * ?` 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发
 *              `0 0-5 14 * * ?` 在每天下午2点到下午2:05期间的每1分钟触发
 *              `0 10,44 14 ? 3 WED` 每年三月的星期三的下午2:10和2:44触发
 *              `0 15 10 ? * MON-FRI` 周一至周五的上午10:15触发
 *              `0 15 10 15 * ?` 每月15日上午10:15触发 `0 15 10 L * ?` 每月最后一日的上午10:15触发
 *              `0 15 10 ? * 6L` 每月的最后一个星期五上午10:15触发
 *              `0 15 10 ? * 6L 2002-2005` 2002年至2005年的每月的最后一个星期五上午10:15触发
 *              `0 15 10 ? * 6#3` 每月的第三个星期五上午10:15触发
 *              `"`0 6 * * *` 每天早上6点
 *              `0 /2 * *` 每两个小时
 *              `0 23-7/2，8 * * *` 晚上11点到早上8点之间每两个小时，早上八点
 *              `0 11 4 * 1-3` 每个月的4号和每个礼拜的礼拜一到礼拜三的早上11点
 *              `0 4 1 1 *` 1月1日早上4点
 * </pre>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2023年10月04日 20:11:31
 * @version 1.0.0
 * </pre>
 */
package cn.peyton.plum.core.quartz;