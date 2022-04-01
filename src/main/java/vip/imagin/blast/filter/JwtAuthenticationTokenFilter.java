package vip.imagin.blast.filter;/**
 * @author lingqu
 * @date 2022/3/1
 * @apiNote
 */

import vip.imagin.blast.modules.user.entity.MyUserDetails;
import vip.imagin.blast.utils.JwtUtil;
import vip.imagin.blast.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author zzhi
 * @version 1.0
 * @description TODO
 * @createDate 2022/3/1
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        //解析token
        String userId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }

        //从redis获取用户信息
        String redisKey = "login:" + userId;
        MyUserDetails myUserDetails = redisCache.getCacheObject(redisKey);
        if (Objects.isNull(myUserDetails)) {
            throw new RuntimeException("用户未登录");
        }

        //存入SecurityContextHolder
        //获取权限信息
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(myUserDetails, null, myUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(request, response);
    }
}
