package vip.imagin.blast.modules.selectsomething.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import vip.imagin.blast.modules.selectsomething.entity.ExpType;

/**
 * (ExpType)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-01 22:01:42
 */
@Mapper
@Repository
public interface ExpTypeDao extends BaseMapper<ExpType> {

}

