package com.example.springboot_vue.utils;

import com.example.springboot_vue.pojo.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    RedisUtils redisUtils = new RedisUtils();

    public static final String SUBJECT = "test";
    // 密钥
    public static String SECRET = "test";
    // 过期时间设置为60分钟
    public static final long EXPIRE = 1000 * 60 * 60;

    public String getToken(User user) {

        if (user == null) {
            return null;
        }

        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("id", user.getId())
                .claim("nickname", user.getNickname())
                .claim("phone", user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                // 设置签名方式以及密钥 这里密钥采用用户密码
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();

        // 将用户的账号和密码存入缓存, 并设置过期时间（30分钟）
//        redisUtils.set(user.getPhone(), token, EXPIRE);

        return token;
    }

    // 由返回的结果可以拿到token中的数据
    public static Claims checkToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    public boolean verifyToken(String token) {
        Claims claims = checkToken(token);
        String account = (String) claims.get("phone");
        String redisToken = (String) redisUtils.get(account);
        return redisToken.equals(token);
    }

    /**
     * 判断token是否已经失效
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = checkToken(token);
        return claims.getExpiration();
    }

    /***
     * 刷新token时间
     */
//    public String refreshToken(String token) {
//
//        if (token == null) {
//            return null;
//        }
//
//        Claims claims = checkToken(token);
//        if (claims == null) {
//            return null;
//        }
//
//        //如果token已经过期，不支持刷新
//        if (isTokenExpired(token)) {
//            return null;
//        }
//
//        //如果token在30分钟之内刚刷新过，返回原token
//        if(tokenRefreshJustBefore(token,30*60)){
//            return token;
//        }else{
//            claims.put(CLAIM_KEY_CREATED, new Date());
//            return generateToken(claims);
//        }
//    }
}
