package cn.peyton.plum.core.err;

import cn.peyton.plum.core.json.JSONResult;
import cn.peyton.plum.core.json.JsonMapper;
import cn.peyton.plum.core.utils.HttpServletResponseTools;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


/**
 * <h3>全局 异常类</h3>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @create date: 2018/11/16 15:12
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
@Slf4j
public class GlobalException extends RuntimeException {


    public GlobalException() {
        super();
    }

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(String message, Throwable cause) {
        super(message, cause);
    }

    public GlobalException(Throwable cause) {
        super(cause);
    }

    protected GlobalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * <h4>验证异常 PrintWriter 写出信息</h4>
     * @param response response 对象
     * @param jsonResult jsonResult 对象{封装的数据}
     */
    public GlobalException(HttpServletResponse response, JSONResult jsonResult) {
        HttpServletResponseTools.returnJson(
                response, JsonMapper.toJSon(jsonResult));
    }

    /**
     * <h4>异常构造</h4>
     * @param response
     * @param result 要返回的包装好的JSON数据
     * @param url  链接地址 如: /err 或 /app/add
     */
    public GlobalException(HttpServletResponse response, JSONResult result, String url) {

        try {
            StringBuilder _sb = new StringBuilder();
            if (null != url) {
                _sb.append(url);
                if (null != result) {
                    _sb.append("?result=" + result);
                }
                response.sendRedirect(_sb.toString());
            }else {
                HttpServletResponseTools.returnJson(
                        response, JsonMapper.toJSon(result));
            }
        } catch (IOException e) {
            log.error("异常在【cn.peyton.core.err.child.GlobelException】 构造方法。");
        }
    }
}
