package vip.imagin.blast.modules.user.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import vip.imagin.blast.dto.LoginUser;
import vip.imagin.blast.modules.user.entity.User;
import vip.imagin.blast.modules.user.service.UserService;
import org.springframework.web.bind.annotation.*;
import vip.imagin.blast.utils.Result;

import javax.annotation.Resource;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2022-03-30 16:16:24
 */
@RestController
@RequestMapping("user")
public class UserController{
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param user 查询实体
     * @return 所有数据
     */
    @GetMapping("test")
    public Result<Page> list(Page<User> page, User user) {
        return new Result<>(200,"成功",userService.page(page, new QueryWrapper<>(user))) ;
    }

    /**
     * 登录接口
     * @param loginUser 这是一个dto是一个入参对象
     * @return
     */
    @PostMapping("login")
    public Result login(LoginUser loginUser){
        return userService.login(loginUser);
    }


}

