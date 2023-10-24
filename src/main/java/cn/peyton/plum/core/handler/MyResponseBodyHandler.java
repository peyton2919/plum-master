package cn.peyton.plum.core.handler;

import cn.peyton.plum.core.anno.img.ImageHostPath;
import cn.peyton.plum.core.json.JSONResult;
import cn.peyton.plum.core.utils.HttpServletRequestUtils;
import cn.peyton.plum.core.utils.LogUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Field;
import java.util.List;

/**
 * <h4>用于拦截返回到页面之前的数据处理</h4>
 * <pre>
 *     这里用于 前后端分离 数据库图片名称为 `/images/xxx/xx.jpg`,
 *     使用方法:
 *       字段 = `/images/xxx/xx.jpg`
 *       在需要改含图片对象的类上添加 @ImageHostPath(name="xxx,xxx") 多个用 `,` 隔开;
 *       xxx 为属性名称;  经处理后 该字段 = `域名/images/xxx/xx.jpg`;
 *       该字段 如果含有 以 `http` 开头的, 则该方法不做处理；
 * </pre>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2023年10月23日 15:25
 * @version 1.0.0
 * </pre>
 */
@ControllerAdvice
public class MyResponseBodyHandler implements ResponseBodyAdvice<Object> {
    //@Value("${web.host}")   // 在application.yml 中配置 web.host
    //private String HOST;
    /** 根目录 */
    private String basePath;
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest _request, ServerHttpResponse response) {

        //通过RequestContextHolder获取request
        HttpServletRequest request = HttpServletRequestUtils.getRequest();
        //String basePath = request.getScheme()+"://" +
        //        request.getServerName() + ":" + request.getServerPort() +
        //        request.getContextPath() + "/";
        basePath = request.getScheme()+"://" +
                request.getServerName() + ":" + request.getServerPort();
        System.out.println("basePath  " + basePath);

        // 遇到feign接口之类的请求, 不应该再次包装,应该直接返回
        // 上述问题的解决方案: 可以在feign拦截器中,给feign请求头中添加一个标识字段, 表示是feign请求
        // 在此处拦截到feign标识字段, 则直接放行 返回body.

        if (body instanceof JSONResult<?>) {
            JSONResult jsonResult = (JSONResult) body;
            Object _object = jsonResult.getData();
            if (_object instanceof List<?>) {
                List<?> _objects = (List<?>) _object;
                if(null != _objects && _objects.size()>0){
                    for (Object _obj : _objects) {
                        try {
                            int count = 0;
                            recurrence(_obj,count);
                        } catch (IllegalAccessException e) {
                            LogUtils.error(e.getMessage());
                        }
                    }
                }
            }else if (_object instanceof Class<?>){
                try {
                    int count = 0;
                    recurrence(_object,count);
                } catch (IllegalAccessException e) {
                    LogUtils.error(e.getMessage());
                }
            }
        }
        return body;
    }

    /**
     * <h4>递归查找 标记 @ImageHostPath 注解</h4>
     * <pre>
     *     递归最多10次
     * </pre>
     * @param obj
     * @throws IllegalAccessException
     */
    private void recurrence(Object obj,int count) throws IllegalAccessException {
        count ++;
        if(count > 10){
            return;
        }
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        if (declaredFields != null) {
            for (Field field : declaredFields) {
                Class<?> type = field.getType();
                String name = type.getName();
                if (name.contains("cn.peyton")) {
                    // 设置
                    field.setAccessible(true);
                    Object fieldObj = field.get(obj);
                    // 调用递归
                    recurrence(fieldObj,count);
                    addImgPathPrefix(obj);
                } else if (("java.util.List").equals(name)) {
                    field.setAccessible(true);
                    List<Object> listObj = (List<Object>) field.get(obj);
                    for (Object lo : listObj) {
                        int _tmpCount = 0;
                        recurrence(lo,_tmpCount);
                    }
                }
            }
        }
    }


    /**
     * <h4>添加图片 前缀</h4>
     * @param _obj
     */
    private void addImgPathPrefix(Object _obj){
        Class<?> clazz  = _obj.getClass();
        ImageHostPath annotation = clazz.getAnnotation(ImageHostPath.class);
        if (null != annotation) {

            String[] _splits = annotation.name().split(",");
            for(String _str : _splits){
                try {
                    Field _field = clazz.getDeclaredField(_str);
                    _field.setAccessible(true);
                    String _tmp = (String) _field.get(_obj);
                    if (!_tmp.startsWith("http")){
                        _field.set(_obj,basePath + _tmp);
                    }
                } catch (NoSuchFieldException e) {
                    LogUtils.error(e.getMessage());
                } catch (IllegalAccessException e) {
                    LogUtils.error(e.getMessage());
                }
            }
        }
    }
}
