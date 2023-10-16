package cn.peyton.plum.core.aop.valid;

import cn.peyton.plum.core.json.JSONResult;
import cn.peyton.plum.core.page.ResponseStatus;
import cn.peyton.plum.core.utils.HttpServletRequestUtils;
import cn.peyton.plum.core.utils.HttpServletResponseUtils;
import cn.peyton.plum.core.utils.base.Maps;
import cn.peyton.plum.core.validator.anno.Valid;
import cn.peyton.plum.core.validator.Validation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * <h4>表单提交验证 aop 切面类</h4>
 * <pre>
 *     通过 @Valid 注解方式(注释在 controller 层上需要验证的方法上);
 *     配合 ValidationAspect 类 aop 切片使用;
 * </pre>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.children.chatter.aop.valid.ValidationAspect
 * @date 2023年10月08日 10:28
 * @version 1.0.0
 * </pre>
 */
@Aspect
@Component
@Slf4j
public class ValidationAspect {

    /**
     * 切面点
     */
    @Pointcut("@annotation(cn.peyton.plum.core.validator.anno.Valid)")
    public void pointCut() { }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 初始化数据
        MethodSignature _signature = (MethodSignature) point.getSignature();
        Method _method = _signature.getMethod();
        Object[] _args = point.getArgs();
        HttpServletResponse _response = HttpServletResponseUtils.getResponse();
        HttpServletRequest _request = HttpServletRequestUtils.getRequest();

        // 获取参数类型集合
        Class<?>[] _parameterTypes = _method.getParameterTypes();

        // 判断 是否有参数; 如果 没有参数 就不做验证,程序继续
        if (!(null != _parameterTypes && _parameterTypes.length > 0)){ return point.proceed(_args); }

        // 获取request [post,get] 请求参数 key value
        Map<String, String[]> _parameterValueMap = _request.getParameterMap();

        // 判断基础类型参数
        Parameter[] _parameters = _method.getParameters();

        for (Parameter _p : _parameters) {
            String _filedType = _p.getType().getTypeName();
            //判断如果是 对象类型 这个拦截器则暂时不做处理
            if (!HttpServletRequestUtils.isBaseType(_filedType)) { continue; }

            String _filedName = _p.getName();
            String[] _ps = _parameterValueMap.get(_filedName);
            if (null == _ps||"".equals(_ps[0])||"undefined".equals(_ps[0])) {
                String _errMsg = "参数名称: [" + _filedName + "]不能为空值;";
                // HttpServletResponseTools.returnJson(response
                //        , JsonMapper.toJSon(JSONResult.fail(HttpStatusCode.FAIL.getCode(), _errMsg, null, response.getStatus())));
                return JSONResult.fail( ResponseStatus.VALIDATE_FAIL.getCode(),_errMsg, null, _response.getStatus());
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
            // 判断逻辑
            for (Parameter _pm : _parameters) {
                // Java 基础属性验证方法
                String _typeName = _pm.getType().getName();

                // 去除不要request,response,session
                if (HttpServletRequestUtils.isExclude(_typeName)) {
                    continue;
                }
                String _filedName = _pm.getName();

                if (HttpServletRequestUtils.isBaseType(_typeName)) {// 验证基础 类型
                    String _val = null;
                    String[] _ps = _parameterValueMap.get(_filedName);
                    if (null != _ps && _ps.length > 0) {
                        _val = _ps[0];
                    }
                    Annotation[] _annotations = _pm.getAnnotations();
                    //判断属性上是否有注解, 有标记注解 为 true
                    if (null != _annotations && _annotations.length > 0) {
                        Validation.valid(_errMap, _annotations, _filedName, _typeName, _val, _single);
                    }
                } else { // 验证 对象
                    //调用赋值方法: HttpServletRequestUtil.voluation，并验证方法: Validation.valid
                    _errMap = Validation.valid(HttpServletRequestUtils.voluation(_parameterValueMap,_typeName), _single);
                }
                //_single 为 true 时表示单一验证，有一个验证不通过就直接跳出
                if (_single && _errMap.size() >0) {
                    break;
                }
            }
            if (null != _errMap && _errMap.size() >0){
                // 写到页面
                //HttpServletResponseTools.returnJson(
                //        response, JsonMapper.toJSon(JSONResult.error(HttpStatusCode.get(HttpStatusCode.ERR_VALID.getCode()),_errMap)));
                return JSONResult.error(ResponseStatus.VALIDATE_FAIL,_errMap);
            }
        }
        return point.proceed();
    }

}
