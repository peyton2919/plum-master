/**
 * <h3>核心包</h3>
 * <pre>
 * 1. 处理从后端数据 到前端, 数据需要加工修改可以用
 *      MyResponseBodyHandler 配合注释 @ImageHostPath 一起使用;
 *      这个配合是给图片添加 域名 前缀 ; 数据库字段只存储 `/img/xxx/xx.jpg`;
 * 2. 自动写入 10位的时间戳; 数据库 存放格式为 int(11), 对象可以为 String(yyyy-MM-dd HH:mm:ss);
 *      以切面方式类 AutoWriteTimestampAspect 配合注解  @AutoWriteTimestamp 使用;
 *      应用在 service 层, 在 service 层 方法上标记 @AutoWriteTimestamp 会找相应的对象属性去赋值时间戳;
 * 3. TokenAspect  @Token
 * 4. 前后端的交互时验证 以
 *      以切面方式类 ValidationAspect 配合注解 @Valid 使用;
 *      需要在验证自定义的对象属性上标记相关（cn.peyton.plum.core.validator.constraints）包下的注解, 才能完成验证;
 * 5. 国际化语言配置 MyLocalResolver 配合 config包下的 PropertySourceConfiguration 一起使用;
 * 6. 前后端数据 用 json 格式, 返回类型 定义为 JSONResult<T> , 放在 core.json 包下;
 *      JSONResult 返回的 code(状态) 统一为:
 *          SUCCESS:200;
 *          FAIL:600;
 *          ERROR:700;
 *      如果需要更细的返回码,可以定义在 JSONResult.errorCode 中, 与前端协商一致;
 *      注: 正常处理 code 码；
 * 7. 数据分页用 PageQuery 对象, 放在 core.page 包下;
 *      自定义 ResponseStatus 状态码 定义在 core.page 包下;
 * 8.
 * 9.
 * 10.
 * 11.
 * 12.
 * 13. 前后端跨域问题在(cn.peyton.plum.core.cors)包下;
 * 14. 定时器问题在(cn.peyton.plum.core.quartz)包下;
 * 15. 自定义缓存问题在(cn.peyton.plum.core.cache)包下;
 * 16. 加密解密问题在(cn.peyton.plum.core.cipher)包下;
 * 17. 自定义异常问题在(cn.peyton.plum.core.err)包下;
 * 18. 拦截器问题在(cn.peyton.plum.core.interceptor)包下;
 * 19. 图片处理问题在(cn.peyton.plum.core.img)包下;
 * 20. 工具包问题在(cn.peyton.plum.core.utils)包下;
 * 21.
 * 22.
 * 23.
 * 24.
 * 25.
 * 26.
 * 27.
 * 28.
 * 29.
 * 30.
 *
 *  逻辑层 controller
 *
 * 31. 需要 验证、保存 token, controller 可以继承 VerifyTokenController(cn.peyton.plum.core.base) 包下;
 *      注：用户要继承 IUser 接口, 也便 VerifyTokenController 方法统一调用;
 * 32.
 *
 * </pre>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2023年10月04日 20:11:31
 * @version 1.0.0
 * </pre>
 */
package cn.peyton.plum.core;
