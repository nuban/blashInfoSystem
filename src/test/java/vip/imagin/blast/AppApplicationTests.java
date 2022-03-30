package vip.imagin.blast;

import vip.imagin.blast.modules.user.entity.MyUserDetails;
import vip.imagin.blast.mapper.MenuMapper;
import vip.imagin.blast.mapper.UserMapper;
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
    UserMapper userMapper;

    @Autowired
    RedisCache redisCache;
    @Test
    void contextLoads() {
        //List<User> users = userMapper.selectList(null);
        //System.out.println(users);

        String redisKey = "login:" + 2;
        MyUserDetails myUserDetails = redisCache.getCacheObject(redisKey);
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

    @Autowired
    private MenuMapper menuMapper;
    @Test
    public void testSelectPermsByUserId(){
        List<String> list = menuMapper.selectPermsByUserId(2L);
        for (String userId : list){
            System.out.println(userId);
        }
    }

}
