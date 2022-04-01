package vip.imagin.blast.modules.user.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import vip.imagin.blast.dto.LoginUser;
import vip.imagin.blast.dto.SignUser;
import vip.imagin.blast.modules.user.entity.User;
import vip.imagin.blast.modules.user.service.UserService;
import org.springframework.web.bind.annotation.*;
import vip.imagin.blast.utils.Result;

import javax.annotation.Resource;
import java.sql.ResultSet;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2022-03-30 16:16:24
 */
@Api(tags="用户各种操作的接口")
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
    //需要权限的测试接口
    @PreAuthorize("hasAuthority('explosive:user')")
    public Result<Page> list(Page<User> page, User user) {
        return new Result<>(200,"成功",userService.page(page, new QueryWrapper<>(user))) ;
    }

    /**
     * 登录接口
     * @param loginUser 这是一个dto是一个入参对象
     * @return
     */
    @PostMapping("login")
    @ApiOperation("登录的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user的userName和password，sureCode",value = "userName--用户名" +
                    "password -- 密码 sureCode-----验证码",
                    required = true,paramType = "query" /*表示参数放在扫描地方*/)
    })
    public Result login(@RequestBody LoginUser  loginUser){
        return userService.login(loginUser);
    }

    @ApiOperation("注销的接口")
    @GetMapping("logout")
    public Result logout(){
        return userService.logout();
    }

    @ApiOperation("注册的接口")
    @ApiImplicitParam(name = "user的userName,password,signCode,sureCode",value = "userName--用户名" +
            "password -- 密码 signCode ----- 注册码 sureCode-----验证码",
            required = true,paramType = "query" /*表示参数放在扫描地方*/)
    @PostMapping("signin")
    public Result signin(@RequestBody SignUser signUser){
        return userService.signIn(signUser);
    }


    @ApiOperation("获取验证码的接口")
    @GetMapping("captch")
    public Result getCaptch(){
        return userService.getCaptch();
    }

}

