package org.example.demo05.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Value("${jwt.secret}")
    private String secret;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        String jwt = req.getHeader("Authorization");

        if (StringUtils.hasText(jwt)) {//有
            //校验令牌
            JWTVerifier ver = JWT.require(Algorithm.HMAC256(secret)).build();
            //解码后的jwt
            try {
                DecodedJWT dj = ver.verify(jwt);
                String username = dj.getAudience().getFirst();
                Global.setCurrentUser(username);//存储到当前线程中
                System.out.println("============success============");
                return true;
            } catch (JWTVerificationException e) {
                //jwt有问题
                //throw new RuntimeException(e);
                //pass
                System.out.println("============error============");
            }
        }

        System.out.println("============fail============");
        //响应状态码401
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        return false;
    }
}
