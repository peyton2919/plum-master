package cn.peyton.plum.core.aop.token;

import cn.peyton.plum.core.anno.token.Token;
import cn.peyton.plum.core.json.JSONResult;
import cn.peyton.plum.core.page.ResponseStatus;
import cn.peyton.plum.core.utils.HttpServletRequestUtils;
import cn.peyton.plum.core.utils.HttpServletResponseUtils;
import cn.peyton.plum.core.utils.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * <h4>需要 token 验证 aop 切面 类</h4>
 * <pre>
 *     通过 @Token 注解方式(注释在 controller 层上需要 Token 验证的的方法上);
 *     配合 TokenAspect 类 aop 切片使用;
 * </pre>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.children.chatter.aop.token.TokenAspect
 * @date 2023年10月11日 8:49
 * @version 1.0.0
 * </pre>
 */
@Aspect
@Component
@Slf4j
public class TokenAspect {
    /** 切面点  */
    @Pointcut("@annotation(cn.peyton.plum.core.anno.token.Token)")
    public void pointCut() { }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 初始化数据
        MethodSignature _signature = (MethodSignature) point.getSignature();
        Method _method = _signature.getMethod();
        HttpServletResponse _response = HttpServletResponseUtils.getResponse();
        HttpServletRequest _request = HttpServletRequestUtils.getRequest();

        Token _token = _method.getAnnotation(Token.class);
        // 有 @Token 注解，需要认证
        if (null != _token) {
            _response.setCharacterEncoding("UTF-8");
            TokenUtils tokenUtils = new TokenUtils();
            String accessToken = _request.getHeader(TokenUtils.Property.TOKEN);
            if (null == accessToken) {
                return JSONResult.fail(ResponseStatus.TOKEN_ILLEGAL);
            } else {
                if (!tokenUtils.verify(accessToken)) {
                    return JSONResult.fail(ResponseStatus.TOKEN_EXPIRE);
                }
            }
        }
        Object[] _args = point.getArgs();
        return point.proceed(_args);
    }
}
