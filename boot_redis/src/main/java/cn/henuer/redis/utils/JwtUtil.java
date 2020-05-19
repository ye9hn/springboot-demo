package cn.henuer.redis.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.Date;
import java.util.HashMap;

public class JwtUtil {

    //过期时间5分钟
    private static final long EXPIRE_TIME = 5*60*1000;

    //生成签名,15分钟后过期
    public static String sign(String username,String userId,String password){
        //过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        //使用用户密码作为私钥进行加密
        Algorithm algorithm = Algorithm.HMAC256(password);
        //设置头信息
        HashMap<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        //附带username和userID生成签名
        return JWT.create().withHeader(header).withClaim("userId",userId)
                .withClaim("username",username).withExpiresAt(date).sign(algorithm);
    }

    //校验token
    public static boolean verity(String token,String password){
        try {
            Algorithm algorithm = Algorithm.HMAC256(password);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}
