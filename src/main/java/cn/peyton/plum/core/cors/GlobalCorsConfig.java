package cn.peyton.plum.core.cors;

import org.springframework.context.annotation.Configuration;

/**
 * <h4>跨域配置类 第一种</h4>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.core.cors.GlobalCorsConfig
 * @date 2023年10月12日 21:32
 * @version 1.0.0
 * </pre>
 */
@Configuration
public class GlobalCorsConfig {

    //@Bean
    //public CorsFilter corsFilter() {
    //    // 1. 添加 CORS 配置信息
    //    CorsConfiguration config = new CorsConfiguration();
    //    // 放行哪些原始域
    //    config.addAllowedOriginPattern("*");  // 2.4.0 后的写法
    //    //config.addAllowedOrigin("*");
    //    // 是否发送 Cookie
    //    config.setAllowCredentials(true);
    //    // 放行哪些请求方式
    //    //config.setAllowedMethods();
    //    // 放行哪些原始请求头信息
    //    //config.setAllowedHeaders();
    //    // 暴露哪些头部信息
    //    //config.addExposedHeader();
    //    // 2. 添加映射路径
    //    UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
    //    corsConfigurationSource.registerCorsConfiguration("/**",config);
    //    // 3. 返回新的 corsFilter
    //    return new CorsFilter(corsConfigurationSource);
    //}
}
