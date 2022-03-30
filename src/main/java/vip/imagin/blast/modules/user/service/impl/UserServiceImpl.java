package vip.imagin.blast.modules.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import vip.imagin.blast.dto.LoginUser;
import vip.imagin.blast.modules.user.dao.UserDao;
import vip.imagin.blast.modules.user.entity.User;
import vip.imagin.blast.modules.user.service.UserService;
import org.springframework.stereotype.Service;
import vip.imagin.blast.utils.Result;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2022-03-30 16:16:25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private UserDao userdao;

    @Override
    public Result login(LoginUser loginUser) {

        return null;
    }
}

