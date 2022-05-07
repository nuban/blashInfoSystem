package vip.imagin.blast.modules.selectsomething.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import vip.imagin.blast.modules.selectsomething.entity.Explosive;

/**
 * (Explosive)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-01 21:49:18
 */
@Mapper
@Repository
public interface ExplosiveDao extends BaseMapper<Explosive> {

}

