package cn.peyton.plum.core.resolver;

import cn.peyton.plum.core.utils.StrUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

/**
 * <h4>配置国际化</h4>
 * <pre>
 *     在项目的类路径resources下创建名称为i18n的文件夹,
 *     多语言国际化文件login.properties、login_zh_CN.properties和login_en_US.properties文件
 *     在 application.yml 中配置
 *      spring.messages.basename=i18n.login
 * </pre>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @email <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.children.chatter.resolver.MyLocalResolver
 * @date 2023年10月06日 9:53
 * @version 1.0.0
 * </pre>
 */
@Configuration
public class MyLocalResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        //获取请求中的语言参数
        String language = request.getParameter("lang");
        Locale locale= Locale.getDefault(); //如果没有就使用默认的（根据主机的语言环境生成一个 Locale ）。
        //如果请求的链接中携带了 国际化的参数
        if (!StrUtils.isEmpty(language)){
            //zh_CN
            String[] s = language.split("_");
            //国家，地区
            locale=new Locale(s[0],s[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }

    /**
     * 将自定义的LocaleResolver重新注册成一个类型为LocaleResolver的Bean组件
     */
    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocalResolver();
    }
}
