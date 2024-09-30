package com.mashiro.utils.Jwt;

import com.mashiro.exception.DrawException;
import com.mashiro.result.ResultCodeEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    // 设置过期时间为1小时
    private static final long tokenExpiration = 60 * 60 * 1000L;
    // 设置秘钥
    private static final SecretKey secretKey = Keys.hmacShaKeyFor("ZhengFengMashiroHuangGj504JieBLin".getBytes());


    // 生成token
    public static String createToken(Long userId, String username) {
        String jwt = Jwts.builder().setSubject("LOGIN_USER_INFO").setExpiration(new Date(System.currentTimeMillis() + tokenExpiration)).claim("userId", userId).claim("username", username).signWith(secretKey, SignatureAlgorithm.HS256).compact();
        return jwt;
    }

    // 解析token
    public static Claims parseToken(String token) {
        if (token == null) {
            throw new DrawException(ResultCodeEnum.ADMIN_LOGIN_AUTH);
        }

        try {
            JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();
            return jwtParser.parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new DrawException(ResultCodeEnum.TOKEN_EXPIRED);
        } catch (JwtException e) {
            throw new DrawException(ResultCodeEnum.TOKEN_INVALID);
        }
    }

    public static void main(String[] args) {
        System.out.println(createToken(2L, "user"));
    }
}