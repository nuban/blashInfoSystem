package vip.imagin.blast.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.imagin.blast.dto.LoginUser;
import vip.imagin.blast.modules.user.entity.User;
import vip.imagin.blast.utils.Result;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2022-03-30 16:16:25
 */
public interface UserService extends IService<User> {

    Result login(LoginUser loginUser);
}

