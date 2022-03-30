package vip.imagin.blast.modules.user.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import vip.imagin.blast.modules.user.entity.User;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2022-03-30 16:16:24
 */
@Mapper
public interface UserDao extends BaseMapper<User> {


}

