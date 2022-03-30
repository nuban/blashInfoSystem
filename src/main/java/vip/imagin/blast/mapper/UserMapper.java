package vip.imagin.blast.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import vip.imagin.blast.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
