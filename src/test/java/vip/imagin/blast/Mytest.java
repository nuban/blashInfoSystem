package vip.imagin.blast;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.FastByteArrayOutputStream;
import vip.imagin.blast.modules.meterial.dao.CaseEnterPriceDao;
import vip.imagin.blast.modules.meterial.entity.Caseenterprice;
import vip.imagin.blast.modules.user.dao.UserDao;
import vip.imagin.blast.modules.user.entity.CaptchImg;
import vip.imagin.blast.modules.user.entity.MyUserDetails;
import vip.imagin.blast.modules.user.entity.User;
import vip.imagin.blast.utils.Base64;
import vip.imagin.blast.utils.RedisCache;
import vip.imagin.blast.utils.Result;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
//@PropertySource(value = {"classpath:/config/readme.properties"}, encoding = "utf-8")
//@PropertySource(value =  "classpath:config/readme.properties")
public class Mytest {

    @Value("${jwt.jwtTimeOut}")
    private String TimeOut;
    @Value("${code.switch}")
    private boolean codeSwitch;

    @Autowired
    private UserDao userManager;

    /**
     * lomda表达式错误
     */
    @Test
    public void testprint() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(1==1,User::getUsername, "zs");
        User user = userManager.selectOne(queryWrapper);
        System.out.println(user.toString());
//        List<User> users = userManager.selectList(null);
//        System.out.println(users);
    }

    /**
     * 测试读取配置文件
     */
    @Test
    public void printmyproperties() {
        System.out.println(TimeOut);
        System.out.println(codeSwitch);

    }


    /**
     * 测试验证码
     */
    //默认的显示字母
//    @Resource(name = "captchaProducer")
//    private Producer captchaProducer;
    @Autowired
    private RedisCache redisCache;
    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Test
    public void testsurecode() throws IOException {
        String capText = captchaProducerMath.createText();
        String capStr = capText.substring(0, capText.lastIndexOf("@"));
        String code = capText.substring(capText.lastIndexOf("@") + 1);
//        log.info("表达式 [{}]",capStr);
//        log.info("结果 [{}]",code);
        BufferedImage image = captchaProducerMath.createImage(capStr);
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        ImageIO.write(image, "jpg", os);

        //生成随机的键
        UUID uuid = UUID.randomUUID();
        String sUuid = uuid.toString();
        String varify = sUuid + "codeimg_";
//        log.info("随机数：[{}]",uuid);
        //图片
        String base64img = Base64.encode(os.toByteArray());
        CaptchImg captchImg = new CaptchImg(sUuid, base64img);
        System.out.println(captchImg);
        //存入redis 3分钟过期
        redisCache.setCacheObject(varify, code, 3, TimeUnit.MINUTES);
        Result result = new Result(200, "验证码响应成功", captchImg);
    }

    /**
     * 测试自定义插入数据，返回id
     */
    @Test
    public void testinsertCaserEnterprice(@Autowired CaseEnterPriceDao enterPriceDao) {
        Caseenterprice caseenterprice = new Caseenterprice();
        caseenterprice.setDirection("hhh");
        caseenterprice.setAddress("hhh");
        caseenterprice.setName("hhh");
        caseenterprice.setCaseenterpriceid(2l);

        enterPriceDao.insertEnterprice(caseenterprice);

    }

    @Test
    public void testinsertCaserEnterprice() {
        MyUserDetails cacheObject = redisCache.getCacheObject("login:1");
        System.out.println("  ");
    }

    @Value("${img.path}")
    private String filePath;
    @Test
    public void testread() {
        System.out.println(filePath);
    }

    @Test
    public void testqueryWrapper(@Autowired UserDao userDao ) {
        LambdaQueryWrapper<User> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        dishLambdaQueryWrapper.eq(User::getId,1);
//        int count1 = dishService.count(dishLambdaQueryWrapper);
        User user = userDao.selectOne(dishLambdaQueryWrapper);
        System.out.println(user);
    }

    @Value("${pythonpath.path}")
    String path;
    @Test
    public void test(){
        System.out.println(path);
    }


}
