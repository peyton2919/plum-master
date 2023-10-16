package cn.peyton.plum.core.cors;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <h4>跨域配置类 第二种</h4>
 * <pre>
 *     第三种 在相应的 controller 类, 添加 注释 @CrossOrigin("*")
 * </pre>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.children.chatter.cors.CorsConfig
 * @date 2023年10月12日 21:45
 * @version 1.0.0
 * </pre>
 */
//@Configuration
public class CorsConfig implements WebMvcConfigurer {
    //@Override
    //public void addCorsMappings(CorsRegistry registry) {
    //    registry.addMapping("/")
    //            // 否是发送 cookie
    //            .allowCredentials(true)
    //            .allowedOriginPatterns("*")
    //            .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"})
    //            .allowedHeaders(new String[]{"Access-Control-Allow-Origin"})
    //            .exposedHeaders(new String[]{});
    //}
}
