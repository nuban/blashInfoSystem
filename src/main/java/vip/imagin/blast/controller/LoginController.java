//package vip.imagin.blast.controller;/**
// * @author lingqu
// * @date 2022/3/1
// * @apiNote
// */
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import vip.imagin.blast.entity.User;
//import vip.imagin.blast.service.LoginService;
//import vip.imagin.blast.utils.Result;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
///**
// * @author zzhi
// * @version 1.0
// * @description TODO
// * @createDate 2022/3/1
// */
//
//@RestController
//@Api(value = "hhh" ,tags= "登录接口")
//public class LoginController {
//
//    @Autowired
//    private LoginService loginService;
//
//    @PostMapping("user/login")
//    @ApiOperation("登录方法")
//    public Result login(@RequestBody User user) {
//        return loginService.login(user);
//    }
//
//    @PostMapping("user/register")
//    public Result register(@RequestBody User user) {
//        return loginService.register(user);
//    }
//
//    @GetMapping("user/logout")
//    public Result logout() {
//        return loginService.logout();
//    }
//}
