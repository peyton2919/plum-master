package cn.peyton.plum.core.config;

import cn.peyton.plum.core.interceptor.ParameterInterceptor;
import cn.peyton.plum.core.utils.base.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * <h4>配置全局拦截器 类</h4>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @email <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.children.chatter.config.MyWebMvcConfigurer
 * @date 2023年10月04日 22:26
 * @version 1.0.0
 * </pre>
 */
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    //@Resource
    //private AdminLoginInterceptor adminLoginInterceptor;
    //@Resource
    //private LoginInterceptor loginInterceptor;

    @Bean
    public HandlerInterceptor parameterInterceptor() {
        return new ParameterInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePath = Lists.newArrayList();
        //排除拦截，除了注册登录(此时还没token)，其他都拦截
        excludePath.add("/user/register");  //登录
        excludePath.add("/user/login");     //注册
        excludePath.add("/static/**");  //静态资源
        excludePath.add("/assets/**");  //静态资源
        excludePath.add("/index.html");
        excludePath.add("/");
        excludePath.add("/css/*");
        excludePath.add("/js/*");
        excludePath.add("/img/*");
        // 添加一个拦截器，拦截以/admin为前缀的url路径（后台登陆拦截）
        //registry.addInterceptor(adminLoginInterceptor)
        //        .addPathPatterns("/admin/**")
        //        .excludePathPatterns("/admin/login")
        //        .excludePathPatterns("/admin/dist/**")
        //        .excludePathPatterns("/admin/plugins/**");
        // 页面登陆拦截
        //registry.addInterceptor(loginInterceptor)
        //        .excludePathPatterns("/admin/**")
        //        .excludePathPatterns("/register")
        //        .excludePathPatterns("/login")
        //        .excludePathPatterns("/logout")
        //        .addPathPatterns("/goods/detail/**")
        //        .addPathPatterns("/shop-cart")
        //        .addPathPatterns("/shop-cart/**")
        //        .addPathPatterns("/saveOrder")
        //        .addPathPatterns("/orders")
        //        .addPathPatterns("/orders/**")
        //        .addPathPatterns("/personal")
        //        .addPathPatterns("/personal/updateInfo")
        //        .addPathPatterns("/selectPayType")
        //        .addPathPatterns("/payPage");

        //registry.addInterceptor(parameterInterceptor()).excludePathPatterns(excludePath).addPathPatterns("/**");

        // token 拦截器
        //registry.addInterceptor(tokenInterceptor()).excludePathPatterns(excludePath).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/upload/**").addResourceLocations("file:D:\\upload\\");
        //registry.addResourceHandler("/goods-img/**").addResourceLocations("file:D:\\upload\\");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/").setViewName("index");
        //registry.addViewController("/err").setViewName("index1");
        //registry.addViewController("/find").setViewName("index2");
    }
}
