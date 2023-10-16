package cn.peyton.plum.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * <h4>配置读取 国际化资源</h4>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.children.chatter.config.PropertySourceConfiguration
 * @date 2023年10月14日 12:44
 * @version 1.0.0
 * </pre>
 */
@Configuration
//@ConfigurationProperties(prefix = "user", ignoreUnknownFields = false)
@PropertySource(value = "classpath:i18n/login.properties",encoding = "UTF-8")
public class PropertySourceConfiguration {
}
