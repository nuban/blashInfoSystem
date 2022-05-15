package vip.imagin.blast.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.imagin.blast.dto.LoginUser;
import vip.imagin.blast.dto.SignUser;
import vip.imagin.blast.modules.user.entity.User;
import vip.imagin.blast.utils.Result;

import java.util.Map;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2022-03-30 16:16:25
 */
public interface UserService extends IService<User> {

    /**
     * 登录
     * @param loginUser
     * @return result
     */
    Result login(LoginUser loginUser);

    /**
     * 注销
     * @return result
     */
    Result logout();

    /**
     * 注册
     * @param signUser 自己封装的dto
     * @return result
     */
    Result signIn(SignUser signUser);

    Result getCaptch();

    Map<String, Object> searchface(StringBuilder img);

    Result getUserInfo(String token);
}

