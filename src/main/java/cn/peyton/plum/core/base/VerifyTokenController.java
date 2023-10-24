package cn.peyton.plum.core.base;

import cn.peyton.plum.core.json.JSONResult;
import cn.peyton.plum.core.page.ResponseStatus;
import cn.peyton.plum.core.utils.LogUtils;
import cn.peyton.plum.core.utils.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;

/**
 * <h4>需要 验证用户 token controller 可继承此逻辑类</h4>
 * <pre>
 *     注意：
 *     1. 用户信息需要保存, 验证 token 时, controller 层 需要继承此类;
 *     2. 用户对象要继承 IUser 接口;
 *     3. 保存 token 时, 调用 saveToken 方法, param 为当前用户对象;
 *     4. 验证 token 调用 verifyTokenAndVoluation 方法, 验证通过后 并赋值给 baseUser;
 *     5. token有值 同时子类也可以调用token 值;
 * </pre>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.plum.core.base.BaseController
 * @date 2023年10月23日 21:25
 * @version 1.0.0
 * </pre>
 */
public abstract class VerifyTokenController<IUser> {
    /** 当前用户 */
    protected IUser baseUser;
    /** token 值 */
    protected String token = "";


    /**
     * <h4>核实 token 并 赋值</h4>
     * <pre>
     *     handleToken 方法实现:
     *     1. 获取 token 值;
     *     2. 核实 token 值;
     *     3. 从 token 中获取 UserParam 对象;
     *     4. 把 IUser 对象存入JSONResult 返回;
     *
     *     verifyTokenAndVoluation 方法实现
     *     1. 通过判断 jsonResult.getCode() == 200 ;
     *          并把 IUser 对象赋值给 baseUser;
     *          继承 AppController 子类, 可直接调用 baseUserParam;
     * </pre>
     * @param request
     * @param param 当前当前对象(new 继承IUser的对象)
     */
    protected void verifyTokenAndVoluation(HttpServletRequest request,IUser param) {
        JSONResult<IUser> jsonResult = handleToken(request,param);
        if (jsonResult.getCode() == 200){
            baseUser = (IUser) jsonResult.getData();
        }
    }

    /**
     * <h4>保存token</h4>
     * @param param 继承IUser的对象
     * @return 加密后的字符串(token值）
     */
    protected String saveToken(IUser param) {
        TokenUtils<IUser> _tokenUtils = new TokenUtils<>();
        String key = param.getClass().getName();
        return _tokenUtils.sign(key, param);
    }

    /**
     * <h4>处理 token 逻辑</h4>
     * @param request
     * @param param 继承IUser的对象
     * @return JSONResul对象
     */
    private JSONResult<IUser> handleToken(HttpServletRequest request,IUser param) {
        if (null == param){
            LogUtils.info("用户对象为空");
            return JSONResult.fail(ResponseStatus.TOKEN_ILLEGAL);
        }
        Class<?> clazz = param.getClass();
        String key = clazz.getName();
        token = request.getHeader(TokenUtils.Property.TOKEN);
        if (null == token || "".equals(token)){
            return JSONResult.fail(ResponseStatus.TOKEN_ILLEGAL);
        }
        TokenUtils<IUser> _tokenTools = new TokenUtils<IUser>();
        IUser _user = _tokenTools.getObject(key, token, param);
        if (null == _user) {
            return JSONResult.fail(ResponseStatus.TOKEN_ILLEGAL);
        }
        return JSONResult.success(_user);
    }

}
