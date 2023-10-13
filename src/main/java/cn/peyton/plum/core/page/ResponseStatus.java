package cn.peyton.plum.core.page;


/**
 * <h4>返回时的状态码</h4>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.plum.core.page.ResponseStatus
 * @date 2023年10月13日 17:27
 * @version 1.0.0
 * </pre>
 */
public enum ResponseStatus {
    /** 成功 返回码: 200 {请求已成功，请求所希望的响应头或数据体将随此响应返回。} */
    SUCCESS(200,"请求已成功，请求所希望的响应头或数据体将随此响应返回。"),
    /** 错误  返回码：40998 {} */
    FAIL(600,"程序出错了，请联系管理员(^~^)(^~^)(^~^)。"),
    /** 错误 返回码：40 {（错误请求） 服务器不理解请求的语法。} */
    ERROR(400,"（错误请求） 服务器不理解请求的语法。")

    ;
    /**
     * <h3>根据传入的 code 返回相应的enum值</h3>
     * @param code 状态标识
     * @return
     */
    public static ResponseStatus get(int code) {
        for (ResponseStatus status : values()) {
            if (status.getCode() == code) {
                return  status;
            }
        }
        return null;
    }

    /**
     * <h3>私有的构造函数</h3>
     * @param code 结果状态 状态标识
     * @param msg 结果消息
     */
    private ResponseStatus(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    /** 状态码 */
    private int code;
    /** 描述 */
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
