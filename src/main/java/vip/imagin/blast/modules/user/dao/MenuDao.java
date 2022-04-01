package vip.imagin.blast.modules.user.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import vip.imagin.blast.modules.user.entity.Menu;

import java.util.List;

/**
 * (Menu)表数据库访问层
 *
 * @author makejava
 * @since 2022-03-30 18:44:05
 */
@Mapper
@Repository
public interface MenuDao extends BaseMapper<Menu> {

    @Select("SELECT DISTINCT m.`permission` FROM role_user ur LEFT JOIN `role` r ON ur.`userid` = r.`id` LEFT JOIN `role_menu` rm ON ur.`roleid` = rm.`roleid` LEFT JOIN `menu` m ON m.`id` = rm.`menuid`  WHERE userid = #{id} AND r.`status` = 0 AND m.`status` = 0")
    List<String> selectPermsByUserId(Long id);
}

