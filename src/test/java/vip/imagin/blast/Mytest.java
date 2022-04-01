package vip.imagin.blast;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vip.imagin.blast.modules.user.dao.UserDao;
import vip.imagin.blast.modules.user.entity.User;

import java.util.List;

@SpringBootTest
public class Mytest {

    @Autowired
    private UserDao userManager;

    /**
     * lomda表达式错误
     */
    @Test
    public void testprint(){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, "zs");
        User user = userManager.selectOne(queryWrapper);
        System.out.println(user.toString());
//        List<User> users = userManager.selectList(null);
//        System.out.println(users);
    }

}
