/**
 * <h3>发送邮件包</h3>
 * <pre>
 *     需要在 spring 配置文件中配置 MailListener 参数:
 *     spring:
 *        mail:
 *          host: smtp.tom.com
 *          username: fz2919
 *          password: 123456
 *          protocol: smtp
 *          default-encoding: UTF-8
 *          port: 8081
 *     直接调用 MailHolder.send();
 * </pre>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2023年10月04日 20:11:31
 * @version 1.0.0
 * </pre>
 */
package cn.peyton.plum.core.mail;