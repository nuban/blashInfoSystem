package vip.imagin.blast;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import vip.imagin.blast.modules.meterial.dao.MarterialDao;
import vip.imagin.blast.modules.meterial.entity.Marterial;
import vip.imagin.blast.modules.user.dao.UserDao;
import vip.imagin.blast.modules.user.entity.MyUserDetails;

import vip.imagin.blast.modules.user.entity.User;
import vip.imagin.blast.utils.JwtUtil;
import vip.imagin.blast.utils.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class AppApplicationTests {

    @Autowired
    UserDao userMapper;

    @Test
    public void test(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username","zs");
        System.out.println(userMapper.selectOne(userQueryWrapper));
    }


    @Test
    public void testmp(@Autowired MarterialDao materialDao){
        String [] strings = {"原子弹","炸弹","火药","情况"};
        LambdaQueryWrapper<Marterial> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.and(ilqw -> {
            for (String string : strings) {
                ilqw.or(iilqw -> iilqw.like(null != string, Marterial::getPlaceDescription, string));
            }
        });
        List<Marterial> marterials = materialDao.selectList(queryWrapper);
    }

    @Autowired
    RedisCache redisCache;
    @Test
    void contextLoads() {
        //List<User> users = userMapper.selectList(null);
        //System.out.println(users);

        //这是一个bug但是它会自动恢复，所以，别碰
        String redisKey = "login:" + 1;
        MyUserDetails myUserDetails = redisCache.getCacheObject(redisKey);
        System.out.println(myUserDetails);
    }

    @Test
    public void testPasswordEncoder(){
        PasswordEncoder ps = new BCryptPasswordEncoder();
        String encode = ps.encode("123456");
        System.out.println(encode);
        //String encode2 = ps.encode("1234");
        //System.out.println(encode);
        //System.out.println(encode2);
        //$2a$10$UViL.jTzZHy/m7K29SuwPenDT5s5XcfIoSHoEJImRBjbsnok3Y7Nu
//        System.out.println(ps.matches("1234",
//                "$2a$10$UViL.jTzZHy/m7K29SuwPenDT5s5XcfIoSHoEJImRBjbsnok3Y7Nu"));
    }

//    @Autowired
//    private MenuMapper menuMapper;
//    @Test
//    public void testSelectPermsByUserId(){
//        List<String> list = menuMapper.selectPermsByUserId(2L);
//        for (String userId : list){
//            System.out.println(userId);
//        }
//    }

    @Test
    public void testjwt(){
        String jwt = JwtUtil.createJWT("1");
        System.out.println(jwt);
    }

//    @Test
//    public void testjson(@Autowired StringRedisTemplate1 red  ){
//        String mydetails = red.get("login:1");
//        JSONObject jsonObject = JSONObject.parseObject(mydetails);
//        MyUserDetails myUserDetails = jsonObject.toJavaObject(MyUserDetails.class);
//        System.out.println(mydetails);
//
//    }

}
