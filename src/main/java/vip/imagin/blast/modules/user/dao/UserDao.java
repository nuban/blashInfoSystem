package vip.imagin.blast.modules.user.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import vip.imagin.blast.modules.user.entity.User;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2022-03-30 16:16:24
 */
@Mapper
@Repository
public interface UserDao extends BaseMapper<User> {


    /**
     * 注册时候检查是否重名
     * @param userName
     * @return
     */
    @Select("select *from user where username = #{userName}")
    User findbyName(String userName);

}

