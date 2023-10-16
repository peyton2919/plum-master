package cn.peyton.plum.core.utils;

import cn.peyton.plum.core.err.GlobalException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * <h3></h3>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @project name: plum
 * @class name: cn.peyton.plum.core.utils.HttpServletResponseUtils.java
 * @create date: 2022/3/21 22:55
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
public final class HttpServletResponseUtils implements Serializable {

    /**
     * <h4>写出 response </h4>
     * @param json
     */
    public static void returnJson(String json) {
        HttpServletResponse response = getResponse();
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try {
            writer = response.getWriter();
            writer.print(json);
        } catch (IOException e) {
            throw new GlobalException("cn.peyton.plum.core.utils.HttpServletResponseUtils: " +
                    "{方法名：returnJson}; 写writer异常[IOException]");
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
    }

    /**
     * <h4>获取当前的response</h4>
     * <pre>
     *      必需引用 spring-web.jar 包
     * </pre>
     * @return
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes _sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return  _sra.getResponse();
    }
}
