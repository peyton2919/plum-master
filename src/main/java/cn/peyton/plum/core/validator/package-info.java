/**
 * <h3></h3>
 * <pre>
 *     对外接口：
 *     1. Validation 验证类
 *     2. Valid 标记验证注解
 *     3. Regulation 正则规则类
 *     调用：
 *     1. Validation[valid] -> BaseValidator[validate] -> ValidatorFactory[valid] -> AbstractValidator[compare];
 *     2.Validation[check] -> BaseValidator[validate] -> ValidatorFactory[valid] -> AbstractValidator[compare];
 * </pre>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2023年10月04日 20:11:31
 * @version 1.0.0
 * </pre>
 */
package cn.peyton.plum.core.validator;