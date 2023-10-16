package cn.peyton.plum.core.interceptor;


import cn.peyton.plum.core.json.JSONResult;
import cn.peyton.plum.core.json.JsonMapper;
import cn.peyton.plum.core.page.ResponseStatus;
import cn.peyton.plum.core.utils.HttpServletRequestUtils;
import cn.peyton.plum.core.utils.HttpServletResponseUtils;
import cn.peyton.plum.core.utils.LogUtils;
import cn.peyton.plum.core.utils.base.Maps;
import cn.peyton.plum.core.validator.anno.Valid;
import cn.peyton.plum.core.validator.Validation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * <h3>判断 [post,get,delete,update] 参数及参数校验</h3>
 * <pre>
 *     校验规则: 在该方法上标记 @Valid
 * </pre>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @project name: plum
 * @class name: cn.peyton.plum.core.commons.ParameterInterceptor.java
 * @create date: 2022/3/22 14:20
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
@Slf4j
public class ParameterInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        log.info("===============  进入拦截器{Parameter Interceptor}  ===============");
        //如果不是映射到方法直接通过，可以访问资源
        if (!(handler instanceof HandlerMethod)) { return true; }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method _method = handlerMethod.getMethod();
        //获取参数集合
        Class<?>[] _parameterTypes = _method.getParameterTypes();
        //判断 是否有参数; 如果 没有参数 就不做验证
        if (!(null != _parameterTypes && _parameterTypes.length > 0)){ return true; }

        //获取request [post,get] 请求参数 key value
        Map<String, String[]> _parameterMap = request.getParameterMap();

        // 判断基础类型参数
        Parameter[] _parameters = _method.getParameters();
        for (Parameter _p : _parameters) {
            String _filedType = _p.getType().getTypeName();
            //判断如果是 对象类型 这个拦截器则暂时不做处理
            if (!HttpServletRequestUtils.isBaseType(_filedType)) { continue; }
            String _filedName = _p.getName();
            String[] _ps = _parameterMap.get(_filedName);
            if (null == _ps||"".equals(_ps[0])||"undefined".equals(_ps[0])) {
                String _errMsg = "参数名称: [" + _filedName + "]不能为空值;";
                LogUtils.info(_errMsg);
                HttpServletResponseUtils.returnJson(JsonMapper.toJSon(JSONResult.fail(_errMsg)));
                return false;
            }
        }

        //  ***************************************************************************  //
        //获取 是否标记 @Valid 的方法
        Valid _validAnnotation = _method.getAnnotation(Valid.class);
        // 有 @Valid 注解，需要验证
        if (null != _validAnnotation && _validAnnotation.require()) {
            Map<String,String> _errMap = Maps.createLinkHashMap();
            //验证参数 true: 验证单个错误就返回当前错误信息; false: 验证全部完全后返回全部错误信息;
            boolean _single = _validAnnotation.single();
            //判断逻辑
            for (Parameter _pm : _parameters) {
                //Java 基础属性验证方法
                String _typeName = _pm.getType().getName();
                //去除不要request,response,session
                if (HttpServletRequestUtils.isExclude(_typeName)) {
                    continue;
                }
                String _filedName = _pm.getName();
                if (HttpServletRequestUtils.isBaseType(_typeName)) {
                    String _val = null;
                    String[] _ps = _parameterMap.get(_filedName);
                    if (null != _ps && _ps.length > 0) {
                        _val = _ps[0];
                    }
                    Annotation[] _annotations = _pm.getAnnotations();
                    //判断属性上是否有注解, 有标记注解 为 true
                    if (null != _annotations && _annotations.length > 0) {
                        Validation.valid(_errMap, _annotations, _filedName, _typeName, _val, _single);
                    }
                } else {
                    //调用赋值方法: HttpServletRequestUtil.voluation，并验证方法: Validation.valid
                    _errMap = Validation.valid(HttpServletRequestUtils.voluation(_parameterMap,_typeName),
                            _validAnnotation.single());
                }
                //_single 为 true 时表示单一验证，有一个验证不通过就直接跳出
                if (_single && _errMap.size() >0) {
                    break;
                }
            }
            if (null != _errMap && _errMap.size() >0){
                // 写到页面
                HttpServletResponseUtils.returnJson(JsonMapper.toJSon(JSONResult.fail(ResponseStatus.VALIDATE_FAIL,_errMap)));
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {

    }
}
