package vip.imagin.blast.modules.selectsomething.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import vip.imagin.blast.modules.selectsomething.entity.Selectcasemantemp;

/**
 * (Selectcasemantemp)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-22 16:56:59
 */
@Mapper   
@Repository
public interface SelectcasemantempDao extends BaseMapper<Selectcasemantemp> {

    @Select("select count(*) from selectcasemantemp where identity_number= #{identityNumber} and user_id = #{id}")
    int selectId(Long id ,String identityNumber);
}

