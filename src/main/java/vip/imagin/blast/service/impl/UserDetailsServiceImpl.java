package vip.imagin.blast.service.impl;/**
 * @author lingqu
 * @date 2022/3/1
 * @apiNote
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import vip.imagin.blast.entity.LoginUser;
import vip.imagin.blast.entity.User;
import vip.imagin.blast.mapper.MenuMapper;
import vip.imagin.blast.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author zzhi
 * @version 1.0
 * @description TODO
 * @createDate 2022/3/1
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userManager;

    @Autowired
    private MenuMapper menuManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, username);
        User user = userManager.selectOne(queryWrapper);


        if (Objects.isNull(user)) {
            throw new RuntimeException("用户名或者密码错误");
        }

        //TODO 权限查询
        List<String> list = menuManager.selectPermsByUserId(user.getId());
        //封装为UserDetails
        return new LoginUser(user, list);
    }
}
