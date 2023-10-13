package cn.peyton.plum.core.err;

import cn.peyton.plum.core.json.JSONResult;
import cn.peyton.plum.core.json.JsonMapper;
import cn.peyton.plum.core.utils.HttpServletResponseTools;
import jakarta.servlet.http.HttpServletResponse;

/**
 * <h3>参数 异常类</h3>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @create date: 2018/11/16 15:12
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
public class ParamException extends RuntimeException {
    public ParamException() {
        super();
    }

    public ParamException(String message) {
        super(message);
    }

    public ParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamException(Throwable cause) {
        super(cause);
    }

    /**
     * <h4>验证异常 PrintWriter 写出信息</h4>
     * @param response response 对象
     * @param jsonResult jsonResult 对象{封装的数据}
     */
    public ParamException(HttpServletResponse response, JSONResult jsonResult) {
        HttpServletResponseTools.returnJson(
                response, JsonMapper.toJSon(jsonResult));
    }

    protected ParamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
