package vip.imagin.blast.modules.user.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import vip.imagin.blast.dto.LoginUser;
import vip.imagin.blast.dto.SignUser;
import vip.imagin.blast.modules.user.dao.MenuDao;
import vip.imagin.blast.modules.user.dao.UserDao;
import vip.imagin.blast.modules.user.entity.MyUserDetails;
import vip.imagin.blast.modules.user.entity.User;
import vip.imagin.blast.modules.user.service.UserService;
import org.springframework.web.bind.annotation.*;
import vip.imagin.blast.utils.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2022-03-30 16:16:24
 */
@Slf4j
@Api(tags="用户各种操作的接口")
@RestController
@RequestMapping("user")
public class UserController{

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private UserDao userDao;

    @Value("${jwt.jwtTimeOut}")
    private Integer TimeOut;
    @Autowired
    private RedisCache redisCache;
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
            @ApiImplicitParam(name = "user的userName和password，sureCode，verifyKey",value = "userName--用户名" +
                    "password -- 密码 sureCode-----验证码 verifyKey------和验证码一起返回 " ,
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
    @PreAuthorize("hasAuthority('explosive:user')")
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


    @ApiOperation("在已经登录后添加，注册人脸的功能")
    @PreAuthorize("hasAuthority('explosive:user')")
    @PostMapping("addFace")
    public Result addFace(@RequestBody @RequestParam(name = "img")  StringBuilder img){
        //获取当前登录用户的id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        Long userId = principal.getUser().getId();

        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
        try {
            Map<String, Object> map = new HashMap<>();
            String substring = img.substring(img.indexOf(",")+1, img.length());
            map.put("image", substring);
            map.put("group_id", "good_person");
            map.put("user_id", userId);
            map.put("user_info", principal.getUser().getNickname());
            map.put("liveness_control", "NONE");
            map.put("image_type", "BASE64");
            map.put("quality_control", "NONE");
            map.put("action_type", "APPEND");
            map.put("face_sort_type", 0);

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = AiFace.getAuth();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return new Result(Status.SUCCESS,result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(Status.FAEFAIL, "人脸注册失败");
    }


    @ApiOperation("人脸登录部分，登录成功返回token")
    @PostMapping("faceLogin")
    public Result faceLogin(@RequestBody @RequestParam(name = "img")  StringBuilder img){
        System.out.println("图片:"+img);
        Map<String, Object> searchface = userService.searchface(img);
        if(searchface==null||searchface.get("user_id")==null){
            return new Result(Status.LOGINFAIL);
        }
        String userIdStr = searchface.get("user_id").toString();
        long userId = Long.parseLong(userIdStr);

        String score=searchface.get("score").toString().substring(0,2);
        int i = Integer.parseInt(score);
        if(i>80){
            //返回token，存入session
            String jwt = JwtUtil.createJWT(userIdStr);
            Map<String, String> map = new HashMap<String, String>();
            map.put("token", jwt);
            //查询用户信息
            User user = userDao.selectById(userId);
            //从数据库获取到用户的权限
            List<String> list = menuDao.selectPermsByUserId(user.getId());
            //封装为UserDetails
            MyUserDetails myUserDetails = new MyUserDetails(user, list);
            try {
                redisCache.setCacheObject("login:" + userId, myUserDetails, TimeOut, TimeUnit.HOURS);
            }catch (Exception e){
                log.info("存入redis异常，大概率是redis 没打开");
                return new Result(Status.REDIS_ERROR);
            }
            return new Result(Status.SUCCESS_LOGIN,map);
        }
        return  new Result(Status.FAIL);
    }
}

