package vip.imagin.blast.modules.user.service;/**
 * @author lingqu
 * @date 2022/3/1
 * @apiNote
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import vip.imagin.blast.modules.user.entity.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vip.imagin.blast.modules.user.dao.MenuDao;
import vip.imagin.blast.modules.user.dao.UserDao;
import vip.imagin.blast.modules.user.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 登录实现去数据库里面查询数据（替换原本的springsecurity方法）
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userManager;

    @Autowired
    private MenuDao menuManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = userManager.selectOne(queryWrapper);


        if (Objects.isNull(user)) {
            throw new RuntimeException("用户名或者密码错误");
        }

        //TODO 权限查询
        List<String> list = new ArrayList<>(Arrays.asList("*:*"));

//        List<String> list = menuManager.selectPermsByUserId(user.getId());
        //封装为UserDetails

        return new MyUserDetails(user,list);
    }
}
