package cn.peyton.plum.core.filter;

import jakarta.servlet.*;

import java.io.IOException;

/**
 * <h3>SpringBoot 整合 Filter方式</h3>
 * <h4>1、以前方式需要在web.xml中配置以下：</h4>
 * <h4>
 *     <filter>
 *         <filter-name>MyFilter</filter-name>
 *         <filter-class>cn.peyton.core.servlet.MyFilter</filter-class>
 *     </filter>
 * </h4>
 * <h4>
 *     <filter-mapping>
 *          <filter-name>MyFilter</filter-name>
 *          <url-pattern>/test</url-pattern>
 *     </filter-mapping>
 * </h4>
 * <h4>2、注解方式直接的类的头部添加</h4>
 * <h4>@WebServlet(name = "MyFilter",urlPatterns = "/test")</h4>
 * <h4>3、需要在启动类中添加 @ServletComponentScan</h4>
 * <h5>启用这个注解意思：在SpringBoot启动会扫描@WebFilter，并将该类实例化</h5>
 * <pre>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2021/11/2 21:31
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
//@WebFilter(filterName = "MyFilter",urlPatterns = {".do",".jsp"})
//@WebFilter(filterName = "MyFilter",urlPatterns = "/test")
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {

    }
}
