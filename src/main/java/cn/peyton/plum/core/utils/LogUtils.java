package cn.peyton.plum.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * <h4></h4>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.core.toolkit.LogTools
 * @date 2023年10月09日 9:30
 * @version 1.0.0
 * </pre>
 */
@Slf4j
@Component
public final class LogUtils implements Serializable {

    /**
     * <4>异常写入 log中 </4>
     * @param depict 异常描述
     */
    public static void error(Object... depict) {
        Throwable _throwable = new Throwable();
        log.error(">>>>>> [ (自定义) -> (异常>) ] 在类: {},方法名: {},所在应类行号: {}。原因: 【{}】",
                getClassName(_throwable), getMethodName(_throwable), getLineNumber(_throwable),
                StrUtils.convertList2Str(depict));
    }

    /**
     * <4>信息写入 log中 </4>
     * @param depict 信息描述
     */
    public static void info(Object... depict) {
        Throwable _throwable = new Throwable();
        log.info(">>>>>> [ (自定义) -> (信息) ] 在类: {},方法名: {},所在应类行号: {}。原因: 【{}】",
                getClassName(_throwable), getMethodName(_throwable), getLineNumber(_throwable),
                StrUtils.convertList2Str(depict));
    }

    /**
     * <h4>获取类名</h4>
     * @param _throwable
     * @return
     */
    static String getClassName(Throwable _throwable) {
        return _throwable.getStackTrace()[1].getClassName();
    }

    /**
     * <h4>获取方法名</h4>
     * @param _throwable
     * @return
     */
    static String getMethodName(Throwable _throwable) {
        return _throwable.getStackTrace()[1].getMethodName();
    }

    /**
     * <h4>获取行号</h4>
     * @param _throwable
     * @return
     */
    static Integer getLineNumber(Throwable _throwable) {
        return _throwable.getStackTrace()[1].getLineNumber();
    }


}
