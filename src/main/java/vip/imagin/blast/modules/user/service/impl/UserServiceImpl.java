package vip.imagin.blast.modules.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.FastByteArrayOutputStream;
import vip.imagin.blast.dto.LoginUser;
import vip.imagin.blast.dto.SignUser;
import vip.imagin.blast.modules.user.dao.UserDao;
import vip.imagin.blast.modules.user.entity.CaptchImg;
import vip.imagin.blast.modules.user.entity.MyUserDetails;
import vip.imagin.blast.modules.user.entity.User;
import vip.imagin.blast.modules.user.service.UserService;
import org.springframework.stereotype.Service;
import vip.imagin.blast.utils.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2022-03-30 16:16:25
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    /**
     * 从配置文件中读取数据
     */
    @Value("${jwt.jwtTimeOut}")
    private Integer TimeOut;
    @Value("${jwt.codeTimeOut}")
    private Integer codeTimeOut;
    @Value("${code.switch}")
    private boolean codeSwitch;

    /**
     *     获取验证码
     */
    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

//    @Value("${jwt.timeOut}")
//    private Long timeOut;
    @Autowired
    private UserDao userdao;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    /**
     * 登录接口
     * @param loginUser
     * @return
     */
    @Override
    public Result login(LoginUser loginUser) {

        //检测验证码
        if(codeSwitch){
            if( null==loginUser.getSureCode() ||"".equals(loginUser.getSureCode())  ){
                return new Result(Status.CODE_EMPTY);
            }
            String code = redisCache.getCacheObject(loginUser.getVerifyKey());
            if(null == code || !code.equals(loginUser.getSureCode())){
                return new Result(Status.CODE_FAILURE);
            }
        }


        //使用Authentication authenticate认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser.getUserName(), loginUser.getPassword());
        Authentication authenticate = null;
        try {
            authenticate = authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            return  new Result(500,"登录失败") ;

        }

        //如果登录成功 生成jwt
        MyUserDetails myUserDetails = (MyUserDetails) authenticate.getPrincipal();
        //验证账号是否正常
        if("0".equals(myUserDetails.getUser().getStatus())){
            return new Result(500,"账号状态异常");
        }
        String userId = myUserDetails.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", jwt);

        //把完整用户信息保存到redis
        redisCache.setCacheObject("login:" + userId, myUserDetails, TimeOut, TimeUnit.HOURS);

        //  MyUserDetails cacheObject = redisCache.getCacheObject("login:1");

        return new Result(200, "登录成功", map);
    }


    @Override
    public Result logout() {
        //从SecurityContextHolder获取id(不可能获取不到)
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        Long id = principal.getUser().getId();

        //把redis中的登录信息删除
        redisCache.deleteObject("login:" + id);
        return new Result(200, "注销成功");
    }

    //注册
    @Override
    public Result signIn(SignUser signUser) {
        //TODO 邀请码的校验

        //遇到重名
        User user = userdao.findbyName(signUser.getUserName());
        if(user != null){
            return new Result(500,"用户已注册");
        }
        //加密密码
        PasswordEncoder ps = new BCryptPasswordEncoder();
        String passwordEncoder = ps.encode(signUser.getPassword());
        //没重名
        int insert = userdao.insert(new User(signUser.getUserName(), passwordEncoder,signUser.getSignCode()));
        if(insert != 0){
          return new Result(200,"注册成功");
        }
        return new Result(500,"注册失败，联系管理员。");
    }

    /**
     * 获取验证码
     * @return
     */
    @Override
    public Result getCaptch() {
        //生成算式
        String capText = captchaProducerMath.createText();
        String  capStr = capText.substring(0, capText.lastIndexOf("@"));
        String code = capText.substring(capText.lastIndexOf("@") + 1);
        BufferedImage image = captchaProducerMath.createImage(capStr);
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (Exception e) {
            log.info("验证码写入失败");
        }

        //生成随机的键
        UUID uuid =UUID.randomUUID();
        String sUuid = uuid.toString();
        String varify = "codeimg_"+sUuid;
        //图片
        String base64img = Base64.encode(os.toByteArray());
        CaptchImg captchImg = new CaptchImg(varify, base64img);
        //存入redis 3分钟过期
        redisCache.setCacheObject(varify,code,codeTimeOut, TimeUnit.MINUTES);
        Result result = new Result(200, "验证码响应成功", captchImg);
        return result;
    }
}

