package vip.imagin.blast.modules.user.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
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


    List<String> selectPermsByUserId(Long id);
}

