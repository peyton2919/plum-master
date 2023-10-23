package cn.peyton.plum.core.utils;

import cn.peyton.plum.core.json.JsonMapper;
import cn.peyton.plum.core.utils.base.Maps;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * <h3>Token 工具类</h3>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @project name: plum
 * @class name: cn.peyton.plum.core.token.TokenUtils.java
 * @create date: 2022/3/20 21:26
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
@Component
@Slf4j
public class TokenUtils<T> implements Serializable {
    /**
     * 过期时间一天， 24 * 60 * 60 * 1000
     * TODO 正式运行时修改为15分钟 15*60*1000
     */
    private long expireTime = 900000l;
    /** 发行者 */
    private String issuer = "peyton.yu";
    /** token 密钥 */
    private String tokenSecret = "y1u2p3DefaultPrivateProjectTestChatterPlumCom";


    /**
     * <h4>加密 Token</h4>
     * @param userId 用户编号
     * @param username 用户名称
     * @return
     */
    public String sign( String userId, String username) {
        return sign(userId, username, getExpireTime());
    }
    /**
     * <h4>加密 Token</h4>
     * @param userId 用户编号
     * @param username 用户名称
     * @param expireTime 过期时间（毫秒）
     * @return
     */
    public String sign(String userId, String username,long expireTime) {
        return JWT.create()
                .withIssuer(getIssuer()) //签名
                .withClaim(Property.USERID, userId)
                .withClaim(Property.USERNAME, username)
                .withClaim(Property.TIMESTAMP, System.currentTimeMillis())
                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime)) //过期时间
                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))  //设置过期时间
                .sign(Algorithm.HMAC256(tokenSecret));
    }

    /**
     *  <h4>加密 Token</h4>
     * @param key 键
     * @param obj 值
     * @return
     */
    public String signObj(String key, String obj) {
        return signObj(key,obj,getExpireTime());
    }


    /**
     * <h4>加密 Token</h4>
     * @param key 键
     * @param obj 值
     * @param expireTime 过期时间（毫秒）
     * @return
     */
    public String signObj(String key, String obj, long expireTime) {
        return JWT.create()
                .withIssuer(getIssuer()) //签名
                .withClaim(key, obj)
                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime)) //过期时间
                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))  //设置过期时间
                .sign(Algorithm.HMAC256(tokenSecret));
    }

    /**
     * <h4>加密 Token</h4>
     * @param key 关键字
     * @param t 对象
     * @return 字符串
     */
    public String sign(String key, T t) {
        return sign(key, t, getExpireTime());
    }

    /**
     * <h4>加密 Token</h4>
     * @param key 关键字
     * @param t 对象
     * @param expireTime 过期时间（毫秒）
     * @return 字符串
     */
    public String sign(String key, T t,long expireTime) {
        String json = JsonMapper.toJSon(t);
        return JWT.create()
                .withIssuer(getIssuer()) //签名
                .withClaim(key, json)
                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime)) //过期时间
                .withIssuedAt(new Date(System.currentTimeMillis())) //当前
                .sign(Algorithm.HMAC256(tokenSecret));
    }

    /**
     * <h4>验证 token值是否被篡改</h4>
     * @param token
     * @return 返回 true 表示 没被篡改 ; false 表示 被篡改过;
     */
    public boolean verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(tokenSecret)).withIssuer(getIssuer()).build();
            DecodedJWT jwt = verifier.verify(token);
            Date expiresAt = jwt.getExpiresAt(); //过期时间
            String _tmpMsg = "Token 认证通过; 过期时间: {" + DateUtils.conventDate2Str(expiresAt) + "}";
            LogUtils.info(_tmpMsg);
            return true;
        } catch (Exception exception) {
            LogUtils.error(exception.getMessage());
            return false;
        }
    }

    /**
     * <h4> 根所 key 解析 token</h4>
     * <pre>
     *  注意：
     *  调用这个getObject()方法前;
     *  需要用verify方法,先验证下token值是否被篡改;
     *  否则会抛出异常;
     * </pre>
     * @param key 键
     * @param token
     * @param t 返回对象类型
     * @return 对象
     */
    public T getObject(String key, String token,T t) {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(tokenSecret)).withIssuer(getIssuer())
                    .build().verify(token);
            String value = jwt.getClaim(key).asString();

            return (T) JsonMapper.readValue(value,t.getClass());
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
            return null;
        }
    }

    /**
     * <h4>获取过期时间</h4>
     * <pre>
     *  注意：
     *  调用这个getExpire()方法前;
     *  需要用verify方法,先验证下token值是否被篡改;
     *  否则会抛出异常;
     * </pre>
     * @param token token值
     * @return
     */
    public Date getExpire(String token) {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(tokenSecret)).withIssuer(getIssuer())
                    .build().verify(token);
            return jwt.getExpiresAt();
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
            return null;
        }
    }

    /**
     * <h4>判断当前token是否过期</h4>
     * <pre>
     *  注意：
     *  调用这个isExpire()方法前;
     *  需要用verify方法,先验证下token值是否被篡改;
     *  否则返回的值不准;
     * </pre>
     * @param token token值
     * @return true 表示 过期; false 表示 未过期
     */
    public boolean isExpire(String token)  {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(tokenSecret)).withIssuer(getIssuer())
                    .build().verify(token);

            long _date = jwt.getExpiresAt().getTime();
            long _curr = System.currentTimeMillis();
            return (_curr>_date);
        }catch (Exception e) {
            LogUtils.error(e.getMessage());
            return false;
        }
    }

    /**
     * <h4>获取发行者</h4>
     * <pre>
     *  注意：
     *  调用这个getIssuer()方法前;
     *  需要用verify方法,先验证下token值是否被篡改;
     *  否则会抛出异常;
     * </pre>
     * @param token token值
     * @return
     */
    public String getIssuer(String token) {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(tokenSecret)).withIssuer(getIssuer())
                    .build().verify(token);
            return jwt.getIssuer();
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
            return null;
        }
    }
    /**
     * <h4> 根所 key 为 userId 解析 token</h4>
     * <pre>
     *  注意：
     *  调用这个getUserId()方法前;
     *  需要用verify方法,先验证下token值是否被篡改;
     *  否则会抛出异常;
     * </pre>
     * @param token
     * @return 用户编号
     */
    public String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(tokenSecret)).withIssuer(getIssuer())
                    .build().verify(token);

            return jwt.getClaim(Property.USERID).asString();
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
            return null;
        }
    }

    /**
     * <h4> 根据 key 为 username 解析 token</h4>
     * <pre>
     *  注意：
     *  调用这个getUsername()方法前;
     *  需要用verify方法,先验证下token值是否被篡改;
     *  否则会抛出异常;
     * </pre>
     * @param token
     * @return 用户名称
     */
    public String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(tokenSecret)).withIssuer(getIssuer())
                    .build().verify(token);
            return jwt.getClaim(Property.USERNAME).asString();
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
            return null;
        }
    }
    /**
     * <h4> 根据 key 为 timestamp 解析 token</h4>
     * <pre>
     *  注意：
     *  调用这个getTimestamp()方法前;
     *  需要用verify方法,先验证下token值是否被篡改;
     *  否则会抛出异常;
     * </pre>
     * @param token
     * @return 时间戳
     */
    public Long getTimestamp(String token) {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(tokenSecret)).withIssuer(getIssuer())
                    .build().verify(token);
            return Long.parseLong(jwt.getClaim(Property.TIMESTAMP).toString());
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
            return null;
        }
    }

    /**
     * <h4> 根据 key 解析 token</h4>
     * <pre>
     *  注意：
     *  调用这个getValue()方法前;
     *  需要用verify方法,先验证下token值是否被篡改;
     *  否则会抛出异常;
     * </pre>
     * @param token
     * @return 值
     */
    public String getValue(String key, String token)  {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(tokenSecret)).withIssuer(getIssuer())
                    .build().verify(token);
            return jwt.getClaim(key).asString();
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
            return null;
        }
    }

    /**
     * <h4>解析 token</h4>
     * <pre>
     *  注意：
     *  调用这个parseToken()方法前;
     *  需要用verify方法,先验证下token值是否被篡改;
     *  否则会抛出异常;
     * </pre>
     * <pre>
     *     "userId": "用户编号",
     *     "username": "用户名称",
     *     "timeStamp": "存入时时间"
     * </pre>
     * @param token
     * @return
     */
    public Map<String, String> parseToken(String token) {
        try {
            Map<String,String> map = Maps.createHashMap();
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(tokenSecret)).withIssuer(getIssuer())
                    .build().verify(token);
            map.put(Property.USERID, jwt.getClaim(Property.USERID).asString());
            map.put(Property.USERNAME, jwt.getClaim(Property.USERNAME).asString());
            map.put(Property.TIMESTAMP, jwt.getClaim(Property.TIMESTAMP).asString());
            return map;
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
            return null;
        }

    }

    /**
     * <h4>判断是否 过期</h4>
     * @param accessToken token 值
     * @param expireTime 要过期时间长
     * @return 返回 true 表示 已过期 ; false 表示 未过期 ;
     */
    public boolean isExpire(String accessToken,long expireTime) {
        // 判断 token 是否过期
        long currentTime = System.currentTimeMillis();
        long oldTime = getTimestamp(accessToken);
        if ((currentTime/1000 - oldTime/1000) > expireTime/1000) {
            return true;
        }
        return false;
    }

    // **************************************** get and set **************************************** //

    /**
     * <h4>过期时间</h4>
     * <pre>
     *     如果没配置就默认为 15分钟 (15 *60 *1000)
     * </pre>
     * @return
     */
    public long getExpireTime() {
        return expireTime;
    }

    /**
     * <h4>过期时间</h4>
     * <pre>
     *     如果没配置就默认为 15分钟 (15 *60 *1000)
     * </pre>
     * @param expireTime
     */
    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * @return token 加密 key 值
     */
    public String getTokenSecret() {
        return tokenSecret;
    }

    /**
     * @param tokenSecret token 加密 key 值
     */
    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    /**
     * @return  发行者
     */
    public String getIssuer() {
        return issuer;
    }

    /**
     * @param issuer  发行者
     */
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    /**
     * <h3>key 的属性</h3>
     */
    public interface Property{
        /** 用户编号 key */
        String USERID = "userId";
        /** 用户名称 key */
        String USERNAME = "username";
        /** 时间 key */
        String TIMESTAMP = "timestamp";
        /** Token key */
        String TOKEN = "token";
    }
    // **************************************** get and set **************************************** //
}
