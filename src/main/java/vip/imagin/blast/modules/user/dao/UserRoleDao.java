package vip.imagin.blast.modules.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import vip.imagin.blast.modules.user.entity.RoleUser;

@Mapper
@Repository
public interface UserRoleDao extends BaseMapper<RoleUser> {

}
