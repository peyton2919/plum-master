package cn.peyton.plum.core.err;

import cn.peyton.plum.core.json.JSONResult;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * <h3>验证 异常类</h3>
 * <pre>
 * Author: <a href="http://www.peyton.cn">peyton</a>
 * Email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * Create: 2018-08-19 21:55
 * Version: 1.0.0
 * </pre>
 * @author peyton
 */
public class  ValidationException extends RuntimeException {


    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * <h4>验证异常 PrintWriter 写出信息</h4>
     * @param response response 对象
     * @param jsonResult jsonResult 对象{封装的数据}
     */
    public ValidationException(HttpServletResponse response, JSONResult jsonResult)  {
        //HttpServletResponseTools.returnJson(
        //        response, JsonMapper.toJSon(jsonResult));

        try {
            response.sendRedirect("/err?result="+ jsonResult);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
