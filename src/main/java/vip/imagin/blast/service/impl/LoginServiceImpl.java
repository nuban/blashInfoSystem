package vip.imagin.blast.service.impl;/**
 * @author lingqu
 * @date 2022/3/1
 * @apiNote
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import vip.imagin.blast.modules.user.entity.MyUserDetails;
import vip.imagin.blast.entity.User;
import vip.imagin.blast.mapper.UserMapper;
import vip.imagin.blast.service.LoginService;
import vip.imagin.blast.utils.JwtUtil;
import vip.imagin.blast.utils.RedisCache;
import vip.imagin.blast.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author zzhi
 * @version 1.0
 * @description TODO
 * @createDate 2022/3/1
 */

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserMapper userManager;
    @Autowired
    private RedisCache redisCache;

    @Override
    public Result login(User user) {
        //使用Authentication authenticate认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //登录失败，给出相应提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }

        //如果登录成功 生成jwt
        MyUserDetails myUserDetails = (MyUserDetails) authenticate.getPrincipal();
        String userId = myUserDetails.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", jwt);

        //把完整用户信息保存到redis
        redisCache.setCacheObject("login:" + userId, myUserDetails, 1, TimeUnit.HOURS);

        return new Result(200, "登录成功", map);
    }

    @Override
    public Result logout() {
        //从SecurityContextHolder获取id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        Long id = principal.getUser().getId();

        //把redis中的登录信息删除
        redisCache.deleteObject("login:" + id);
        return new Result(200, "注销成功");
    }

    @Override
    public Result register(User user) {

        //判断用户名是否已经存在了
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, user.getUserName());
        User hasUser = userManager.selectOne(queryWrapper);
        if (!Objects.isNull(hasUser)) {
            return new Result(403, "注册失败，该用户已存在");
        }

        //加密密码
        PasswordEncoder ps = new BCryptPasswordEncoder();
        String passwordEncoder = ps.encode(user.getPassword());
        user.setPassword(passwordEncoder);

        //得到执行人的id(如果可以每个人注册则不需要这个，并且取消注册接口的拦截)
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        Long id = principal.getUser().getId();
        user.setCreateBy(id);
        userManager.insert(user);
        return new Result(200, "注册成功");
    }
}
