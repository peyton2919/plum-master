package cn.peyton.plum.core.aop.auth;

import cn.peyton.plum.core.anno.auth.Permission;
import cn.peyton.plum.core.utils.HttpServletRequestUtils;
import cn.peyton.plum.core.utils.HttpServletResponseUtils;
import cn.peyton.plum.core.utils.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <h4>检查权限 aop 切面类</h4>Inspect
 * permission 准许
 *
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.plum.core.aop.auth.AuthorityAspect
 * @date 2023年10月15日 18:34
 * @version 1.0.0
 * </pre>
 */
@Aspect
@Component
public class AuthenticationAspect {

    // 1. 判断 session.get(key) 是否为空
    // 2. 是否当前用户合法
    // 3. login -> sucess -> 用户信息存到 session中
    // 4. 判断 seesion 是否存值
    // 5.

    /** 切面点  */
    @Pointcut("@annotation(cn.peyton.plum.core.anno.auth.Permission)")
    public void pointCut() { }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 初始化数据
        MethodSignature _signature = (MethodSignature) point.getSignature();
        Method _method = _signature.getMethod();
        HttpServletRequest request = HttpServletRequestUtils.getRequest();
        HttpSession session = request.getSession();
        HttpServletResponse response = HttpServletResponseUtils.getResponse();

        Permission _permission = _method.getAnnotation(Permission.class);
        if (null != _permission) {
            String _key = _permission.key();
            Object _obj = session.getAttribute(_key);
            if (null == _obj) {
                _redirect(request, response, _permission.redirect());
                //request.getSession().setAttribute("errMsg", "请登录");
                //response.sendRedirect(_permission.redirect());
            } else {
                // 获取
                Field _field = _obj.getClass().getDeclaredField(_permission.verify());
                _field.setAccessible(true);
                // 判断 token 值 如为空, 表示 不合法的用户 让他跳转到 登录页面
                Object _tmpKeyValue = _field.get(_obj);
                if (null == _tmpKeyValue) {
                    _redirect(request, response, _permission.redirect());
                    //request.getSession().setAttribute("errMsg", "请登录");
                    //response.sendRedirect(_permission.redirect());
                } else {
                    TokenUtils _tokenUtils = new TokenUtils();
                    if (!_tokenUtils.verify(_tokenUtils.toString())) {
                        _redirect(request, response, _permission.redirect());
                        //request.getSession().setAttribute("errMsg", "请登录");
                        //response.sendRedirect(_permission.redirect());
                    }
                }
            }
        }
        return point.proceed();
    }

    private void  _redirect(HttpServletRequest request,HttpServletResponse response,String url) throws IOException {
        request.getSession().setAttribute("errMsg", "请登录");
        response.sendRedirect(url);
    }


}
