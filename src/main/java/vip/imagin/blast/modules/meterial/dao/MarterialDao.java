package vip.imagin.blast.modules.meterial.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import vip.imagin.blast.modules.meterial.entity.Marterial;
import vip.imagin.blast.modules.user.entity.Menu;

import java.util.List;

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
    List<Marterial> findMarterialByuserid(Long id);

    List<Marterial> selectByDescription(String[] strings);

    @Update("update marterial set passed = 1,examined = 1 where id = #{id}")
    void updateByMarterial1Id(Long id);

    @Update("update marterial set passed = 0,examined = 1,no_pass_reason= #{noPassReason} where id = #{id}")
    void updateByMarterialId(Long id, String noPassReason);
}

