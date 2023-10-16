package cn.peyton.plum.core.utils;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * String getComment()返回cookie中注释,如果没有注释的话将返回空值.
 * String getDomain() 返回cookie中Cookie适用的域名. 
 * 		使用getDomain() 方法可以指示浏览器把Cookie返回给同 一域内的其他服务器,
 * 			而通常Cookie只返回给与发送它的服务器名字完全相同的服务器。注意域名必须以点开始（例如.baidu.com）
 * int getMaxAge() 返回Cookie过期之前的最大时间，以秒计算。
 * int getName()返回Cookie的名字。名字和值是我们始终关心的两个部分，笔者会在后面详细介绍 getName/setName。
 * String getPath()返回Cookie适用的路径。如果不指定路径，Cookie将返回给当前页面所在目录及其子目录下 的所有页面。
 * boolean getSecure() 如果浏览器通过安全协议发送cookies将返回true值，如果浏览器使用标准协议则返回false值。
 * String getUserType() 返回Cookie的值。笔者也将在后面详细介绍getValue/setValue。
 * int getVersion() 返回Cookie所遵从的协议版本。
 * void setComment(String purpose) 设置cookie中注释。
 * void setDomain(String pattern) 设置cookie中Cookie适用的域名
 * void setMaxAge(int expiry) 以秒计算，设置Cookie过期时间。
 * void setPath(String uri) 指定Cookie适用的路径。
 * void setSecure(boolean flag) 指出浏览器使用的安全协议，例如HTTPS或SSL。
 * void setValue(String newValue) cookie创建后设置一个新的值。
 * void setVersion(int v) 设置Cookie所遵从的协议版本。 
 */

/**
 * <h3>Cookie工具类</h3>
 * <pre>
 * @email <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2018/11/16 15:54
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
public final class CookieUtils implements Serializable {

	/**
	 * <h4>显示 BaseCookies</h4>
	 * @param request 请求
	 * @param response 响应
	 */
	public static void showCookies(HttpServletRequest request , HttpServletResponse response) {
        //获取一个cookie数组
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for(Cookie cookie :cookies) {
				System.out.println(cookie.getName() + "," + cookie.getValue());
			}
		}
        List<Cookie> cookieList = Arrays.asList(cookies);
	}

    /**
     * <h4>获取 BaseCookies</h4>
     * @param request 请求
     * @return
     */
    public static List<Cookie> getCookies(HttpServletRequest request) {
        return Arrays.asList(request.getCookies());
    }

    /**
     * <h4>设置cookie</h4>
     * @param response 响应
     * @param name cookie名称
     * @param value cookie值
     */
	public static void addCookie(HttpServletResponse response, String name, String value){
        addCookie(response,name,value,null,null,null);
    }
/**
     * <h4>设置cookie</h4>
     * @param response 响应
     * @param name cookie名称
     * @param value cookie值
     * @param maxAge 过期时间 (单位: 秒)
     */
	public static void addCookie(HttpServletResponse response, String name, String value, Integer maxAge){
        addCookie(response,name,value,maxAge,null,null);
    }

	/**
	 * <h4>设置cookie</h4>
	 * @param response 响应
	 * @param name cookie名称
	 * @param value	cookie值
	 * @param maxAge cookie生命周期 以秒为单位
	 * @param path cookie传递路径 '/'
	 * @param domain cookie域 'localhost'
	 */
	public static void addCookie(HttpServletResponse response, String name, String value,
                                 Integer maxAge, String path , String domain) {
		Cookie cookie = new Cookie(name, value);

        //30min
        if (null != maxAge){
		    cookie.setMaxAge(maxAge);
        }else {
            cookie.setMaxAge(-1);
        }
        if(null != path && !"".equals(path)) {
            cookie.setPath(path);
        }else {
            cookie.setPath("/");
        }
        // 如果cookie的值中含有中文时，需要对cookie进行编码，不然会产生乱码
        try {
            URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            LogUtils.error(e.getMessage());
        }
        if (null != domain && !"".equals(domain)) {
            cookie.setDomain(domain);
        }
		response.addCookie(cookie);
	}
	
	/**
	 * <h4></h4>
	 * 注意一、修改、删除Cookie时，新建的Cookie除value、maxAge之外的所有属性,<br>
	 * 例如name、path、domain等，都要与原Cookie完全一样。<br>
	 * 否则，浏览器将视为两个不同的Cookie不予覆盖，导致修改、删除失败。<br>
	 * @param request 请求
	 * @param response 响应
	 * @param name cookie名称
	 * @param value cookie值
	 */
	public static void editCookie(HttpServletRequest request, HttpServletResponse response,
                                  String name, String value) {
		Cookie[] cookies = request.getCookies();
		if(null != cookies) {
			for(Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					cookie.setValue(value);
					cookie.setPath("/");
					cookie.setMaxAge(30 * 60);
					response.addCookie(cookie);
					break;
				}
			}
		}
	}
	/**
	 * <h4>修改cookie</h4>
	 * 注意一、修改、删除Cookie时，新建的Cookie除value、maxAge之外的所有属性,<br>
	 * 例如name、path、domain等，都要与原Cookie完全一样。<br>
	 * 否则，浏览器将视为两个不同的Cookie不予覆盖，导致修改、删除失败。<br>
	 * @param request 请求
	 * @param response 响应
	 * @param name cookie名称
	 * @param value	cookie值
	 * @param maxAge cookie生命周期 以秒为单位
	 * @param path cookie传递路径 '/'
	 * @param domain cookie域 'localhost'
	 */
	public static void editCookie(HttpServletRequest request, HttpServletResponse response,
                                  String name, String value, int maxAge, String path , String domain) {
		Cookie[] cookies = request.getCookies();
		if(null != cookies) {
			for(Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					cookie.setValue(value);
					cookie.setPath(path);
					cookie.setMaxAge(maxAge);
					cookie.setDomain(domain);
					response.addCookie(cookie);
					break;
				}
			}
		}
	}
	
	/**
	 * <h4>删除cookie</h4>
	 * @param request 请求
	 * @param response 响应
	 * @param name cookie名称
	 * @param path cookie传递路径 '/'
	 */
	public static void delCookie(HttpServletRequest request, HttpServletResponse response, String name, String path) {
		Cookie[] cookies = request.getCookies();
		if(null != cookies) {
			for(Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					cookie.setValue(null);
					cookie.setMaxAge(0);
					cookie.setPath(path);
					response.addCookie(cookie);
					break;
				}
			}
		}
	}
	
	/**
	 * <h4>根据名字获取cookie</h4>
	 * @param request 响应
	 * @param name cookie名字
	 * @return cookie
	 */
	public static Cookie getCookie(HttpServletRequest request , String name) {
		Map<String, Cookie> cookieMap = readCookieMap(request);
		if (cookieMap.containsKey(name)) {
			return cookieMap.get(name);
		}
		return null;
	}
	/**
	 * <h4>根据名字获取cookie</h4>
	 * @param request 响应
	 * @param name cookie名字
	 * @return cookie
	 */
	public static String getCookieByName(HttpServletRequest request , String name) {
		Map<String, Cookie> cookieMap = readCookieMap(request);
		if (cookieMap.containsKey(name)) {
			return cookieMap.get(name).getValue();
		}
		return null;
	}
	/**
	 * <h4>获取cookie集合(封装成Map,key为cookie的名字)</h4>
	 * @param request 请求
	 * @return Map集合
	 */
	public static Map<String , Cookie> getCookieByMap(HttpServletRequest request) {
		return readCookieMap(request);
	}
	
	
	/**
	 * <h4>数组的cookie转成Map型</h4>
	 * @param request 请求
	 * @return cookie Map 集合
	 */
	private static Map<String, Cookie> readCookieMap(HttpServletRequest request){
		Map<String, Cookie> cookieMap = new ConcurrentHashMap<String, Cookie>(8);
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for(Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}
	
}
/**
 * 使用说明
 *  域名wwww1.pclady.com.cn 顶级域名.com.cn 一级域名pclady.com.cn;二级域名 www1.pclady.com.cn
 *
 *  1. domain规则
 *      1). 设置cookie——设置cookie的时候，domain要符合域名的规则，比如可以设置成www1.pclady.com.cn和pclady.com.cn 但是不能设置成pclady。
 *          要有.com.cn或者其他域名做结尾。 通过js手动设置cookie的domain都是以.开头的。比如设置domain=pclady.com.cn,
 *          实际的domain名为.pclady.com.cn；删除cookie时加不加.都可以。
 *      2). 获取cookie——js只能获取domian大于等于当前页面域名的cookie。
 *          比如http://www1.pclady.com.cn/zt/20160623/testCookie.html页面中的js能获取domain为“www1.pclady.com.cn”
 *          和“.www1.pclady.com.cn”和“.pclady.com.cn”但是获取不到“g.pclady.com.cn”中的cookie；
 *      3). 删除cookie——要删除一个cookie，domain值必须跟要删除cookie的domain相同，默认的domain为html文件的domain。
 *      4). 跨域domain——js不可以把cookie设置成不同与html域名的domian。cookie设置不会成功，但不会影响后面程序对cookie的操作。
 *      5). 错误——如果domain设置错误，该cookie将不会被创建，并且后续对cookie的操作不论正确与否都会被浏览器禁止。
 *
 *  2. path规则
 *      1). 设置cookie——js设置path要以"/"开头，比如html路径为"/zt/20160623/",路径可以设置成"/"或"/zt"。
 *      2). 获取cookie——使用js只能获取path大于等于当前页面path的cookie，比如html路径为/zt/20160623/,
 *          使用js只能获取“/zt/20160623/”和“/zt”和“/”路径下的cookie。不能获取其他路径下的cookie
 *      3). 删除cookie——删除cookie的时候路径也必须相同，默认的路径是html的path路径。
 *      4). 错误——如果path不是以"/"开头的则创建cookie的path使用默认的path；如果是以"/"开头但是设置错了，
 *          路径名不存在或者直接设置成子路径。比如设置成"/20160623"或者"/zt1",该cookie将不会被创建，
 *          并且后续对cookie的操作不论正确与否都会被浏览器禁止。
 *
 *  3. maxAge 规则 (单位:秒)
 *      1). -1 会话级 Cookie ,关闭浏览器失效
 *      2). 0 不记录 Cookie
 *      3). 大于0 记录过期时间
 *
 *   注意一、修改、删除Cookie时，新建的Cookie除value、maxAge之外的所有属性，例如name、path、domain等，都要与原Cookie完全一样。
 *      否则，浏览器将视为两个不同的Cookie不予覆盖，导致修改、删除失败。
 *
 *   注意二、从客户端读取Cookie时，包括maxAge在内的其他属性都是不可读的，也不会被提交。
 *      浏览器提交Cookie时只会提交name与value属性。maxAge属性只被浏览器用来判断Cookie是否过期。
 *
 */
