package vip.imagin.blast.modules.meterial.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import vip.imagin.blast.modules.meterial.entity.Marterial;
import vip.imagin.blast.modules.user.entity.Menu;

/**
 * (Marterial)表数据库访问层
 *
 * @author makejava
 * @since 2022-03-30 16:52:21
 */
@Mapper
@Repository
public interface MarterialDao extends BaseMapper<Marterial> {


    @Select("select * from marterial where user_id = #{id}")
    Marterial findMarterialByuserId(Long id);
}

