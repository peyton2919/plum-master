package cn.peyton.plum.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <h4>配置 事务</h4>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @email <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.children.chatter.config.MyTransactionalConfiguration
 * @date 2023年10月04日 22:47
 * @version 1.0.0
 * </pre>
 */
@EnableTransactionManagement //开启事务管理
@Configuration
public class MyTransactionalConfiguration {
}
