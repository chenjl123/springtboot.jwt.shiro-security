package com.cn.shiro.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * jwt工具类
 */
public class JwtUtil {

    /**
     * 过期时间30分钟，这里需要根据具体的需求来
     */
    private static final long EXPIRE_TIME = 30 * 60 * 1000;

    /**
     * 校验token是否正确
     *
     * @param token    密钥
     * @param username 用户名
     * @param secret   用户的密码
     * @return 正确: true；不正确：false
     */
    public static boolean verify(String token, String username, String secret) {
        // 根据密码生成JWT校验器
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            // 校验TOKEN
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取用户名
     *
     * @param token token中包含了用户名
     * @return
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            //e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成签名
     *
     * @param username 用户名
     * @param secret   密码
     * @return 加密的TOKEN
     */
    public static String sign(String username, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带用户信息
        return JWT.create()
                .withClaim("username", username)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    public static void main(String[] args){
        System.out.println(Algorithm.HMAC256("1").toString());
    }
}
