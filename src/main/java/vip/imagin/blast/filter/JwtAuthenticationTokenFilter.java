package vip.imagin.blast.filter;/**
 * @author lingqu
 * @date 2022/3/1
 * @apiNote
 */

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
            log.info("用户随便写了一个token");
            filterChain.doFilter(request, response);
            return;
        }

        //从redis获取用户信息
        String redisKey = "login:" + userId;
        MyUserDetails  myUserDetails = redisCache.getCacheObject(redisKey);
        if (Objects.isNull(myUserDetails)) {
            log.info("用户退出登录，却在访问需要权限的资源");
            filterChain.doFilter(request, response);
            return;
            //throw new RuntimeException("用户未登录");
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
