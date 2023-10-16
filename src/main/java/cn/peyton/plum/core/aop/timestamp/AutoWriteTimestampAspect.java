package cn.peyton.plum.core.aop.timestamp;

import cn.peyton.plum.core.anno.timestamp.AutoWriteTimestamp;
import cn.peyton.plum.core.json.JSONResult;
import cn.peyton.plum.core.utils.DateUtils;
import cn.peyton.plum.core.utils.HttpServletRequestUtils;
import cn.peyton.plum.core.utils.HttpServletResponseUtils;
import cn.peyton.plum.core.utils.LogUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <h4>aop 创建 10位的时间戳 切面类</h4>
 * <pre>
 *     处理流程:
 *     1. 数据库 创建为 int(10) 时间 字段;
 *     2. 对象有 pojo (对应数据层)与param（对应视图层)：
 *          pojo 对象 时间字段为 int 类型;
 *          param 对象 时间字段为 String 类型;
 *     3. 切面 注解{@AutoWriteTimestamp}在 service.impl 下的方法 添加add或更新update 上;
 *     4. @Timestamp 下 value 值为要创建的时间戳 的字段;
 *
 *     通过 @Valid 注解方式(注释在 controller 层上需要验证的方法上);
 *     配合 AutoWriteTimestampAspect 类 aop 切片使用;
 * </pre>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.children.chatter.aop.timestamp.TimestampAspect
 * @date 2023年10月07日 21:14
 * @version 1.0.0
 * </pre>
 */
@Aspect
@Component
@Slf4j
public class AutoWriteTimestampAspect {

    @Pointcut("@annotation(cn.peyton.plum.core.anno.timestamp.AutoWriteTimestamp)")
    public void timePointCut() {  }

    @Around("timePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method _method = signature.getMethod();
        Annotation[] _annos = _method.getAnnotations();

        String[] fieldNames = null;
        for (Annotation _anno : _annos) {
            if (_anno instanceof AutoWriteTimestamp){
                AutoWriteTimestamp _tt = (AutoWriteTimestamp) _anno;
                fieldNames = _tt.value().split(",");
                break;
            }
        }
        // 获取参数类型集合
        Class<?>[] _parameterTypes = _method.getParameterTypes();
        // 只处理含 cn.peyton 类 和 java.util.List -> ArrayList 类
        Object[] _args = point.getArgs();
        if (null != _args && _args.length > 0) {
            // 处理参数类型
            for (int i = 0; i < _parameterTypes.length; i++) {
                Class<?> _pt = _parameterTypes[i];
                String _className = _pt.getName();
                if ("java.util.List".equals(_className)) {
                    List _list = (List) _args[i];
                    for (Object _obj : _list) {
                        multi(_obj,fieldNames);
                    }

                } else if (_className != null && _className.contains("cn.peyton")) {
                    multi(_args[i],fieldNames);
                }
            }
        }
        return point.proceed();
    }

    /**
     * <h4>循环多个</h4>
     * @param obj 对象
     * @param fieldNames 属性名集合
     */
    private void multi(Object obj, String[] fieldNames) {
        for (String _fn : fieldNames) {
            createTimestamp(_fn, obj);
        }
    }

    /**
     * <h4>创建时间戳</h4>
     * @param fieldName 时间戳字段（createTime/updateTime)
     * @param obj  要创建含时间戳的类
     * @return
     */
    private JSONResult createTimestamp(String fieldName, Object obj) {
        Field field = null;
        String _name = obj.getClass().getName();
        _name = _name.substring(_name.lastIndexOf('.') + 1);
        try {
            field = obj.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            //todo
            LogUtils.error(obj);
            return JSONResult.fail(400999, "获取当前对象{" + _name + "}的{" + fieldName + "}属性名称错误");
        }
        field.setAccessible(true);
        try {
            // 需要判断 该属性是 int 还是　String
            String _tmp =field.getType().getName();
            if (_tmp.contains("Integer")|| _tmp.contains("int")){
                field.set(obj,DateUtils.dateToTimestamp());
            }else if (_tmp.contains("String")){
                field.set(obj, DateUtils.timestampToStrDate(new Date()));
            } else if (_tmp.contains("Date")) {
                field.set(obj, new Date());
            }

        } catch (IllegalAccessException e) {
            String _errMsg = " 设置当前对象{" + _name + "}的{" + fieldName + "}属性值错误 ";
            LogUtils.error(obj,_errMsg,e.getMessage());
            return JSONResult.error(_errMsg);
        }
        return null;
    }

    //@Before("execution(* cn.peyton.children.*.add*(..))")
    //@Before("timePointCut()")
    //@Before(value = "timePointCut()")
    public Object before(JoinPoint point)  {
        HttpServletResponse response = HttpServletResponseUtils.getResponse();
        HttpServletRequest request = HttpServletRequestUtils.getRequest();

        Object result = point.getClass();
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Annotation[] _annos = method.getAnnotations();
        String fieldName = null;
        for (Annotation _anno : _annos) {
            if (_anno instanceof AutoWriteTimestamp){
                AutoWriteTimestamp _tt = (AutoWriteTimestamp) _anno;
                if (null == _tt.value() || "".equals(_tt.value())) {
                    fieldName = "createTime";
                }
                fieldName =_tt.value();
                break;
            }
        }
        Object[] _args = point.getArgs();
        if (null != _args && _args.length > 0) {
            Field field = null;
            try {
                field = _args[0].getClass().getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                log.error("获取字段错误");
            }
            field.setAccessible(true);
            try {
                field.set(_args[0], DateUtils.timestampToStrDate(new Date()));
            } catch (IllegalAccessException e) {
                log.error("字段赋值错误");
            }
        }
        log.info("<=====================================================");
        log.info("请求来源： =》" + request.getRemoteAddr());
        log.info("请求URL：" + request.getRequestURL().toString());
        log.info("请求方式：" + request.getMethod());
        log.info("响应方法：" + signature.getDeclaringTypeName() + "." + signature.getName());
        log.info("请求参数：" + Arrays.toString(_args));
        log.info("响应码：" + response.getStatus());
        log.info("------------------------------------------------------");
        return result;
    }
}


