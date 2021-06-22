package com.wen.sso.common.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.wen.sso.common.entity.Payload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Date;

/**
 * JWT 工具类
 *
 * @author wenjun
 * @since 2021/6/20
 */
public class JwtUtil {

    /**
     * JWT 负载属性名
     */
    private static final String JWT_PAYLOAD_USER_KEY = "userInfo";

    /**
     * 过期时间（分）
     */
    public static final String EXPIRATION_TYPE_MINUTE = "minute";

    /**
     * 过期时间（秒）
     */
    public static final String EXPIRATION_TYPE_SECOND = "second";

    /**
     * 私钥加密生成 Token
     *
     * @param userInfo   用户信息
     * @param key        私钥
     * @param expire     过期时间
     * @param expireType 过期时间类型
     * @return Token
     */
    public static String generateToken(Object userInfo, PrivateKey key, int expire, String expireType) {
        Date expiration;
        switch (expireType) {
            case EXPIRATION_TYPE_MINUTE:
                expiration = DateTime.now()
                        .plusMinutes(expire)
                        .toDate();
                break;
            case EXPIRATION_TYPE_SECOND:
                expiration = DateTime.now().
                        plusSeconds(expire)
                        .toDate();
                break;
            default:
                expiration = new Date();
        }
        return Jwts.builder()
                .claim(JWT_PAYLOAD_USER_KEY, JsonUtil.toString(userInfo))
                .setId(generateJti())
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.RS256)
                .compact();
    }

    /**
     * 获取 JWT 负载
     *
     * @param token         Token
     * @param key           公钥
     * @param userInfoClass 用户信息 Class
     * @return JWT 负载
     */
    public static <T> Payload<T> getPayload(String token, PublicKey key, Class<T> userInfoClass) {
        Jws<Claims> jws = parseToken(token, key);
        Claims claims = jws.getBody();
        T userInfo = JsonUtil.toBean(claims.get(JWT_PAYLOAD_USER_KEY).toString(), userInfoClass);
        Payload<T> payload = new Payload<>();
        payload.setId(claims.getId())
                .setUserInfo(userInfo)
                .setExpiration(claims.getExpiration());
        return payload;
    }

    /**
     * 公钥解析 Token
     *
     * @param token Token
     * @param key   公钥
     * @return Jws<Claims>
     */
    private static Jws<Claims> parseToken(String token, PublicKey key) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

    /**
     * 生成 JTI
     *
     * @return JTI
     */
    private static String generateJti() {
        // 获取分布式全局唯一 ID，参数一为终端 ID，参数二为数据中心 ID
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        long id = snowflake.nextId();
        byte[] jti = Base64.getEncoder().encode(String.valueOf(id).getBytes());
        return new String(jti);
    }
}
