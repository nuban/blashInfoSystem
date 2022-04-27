package vip.imagin.blast.modules.meterial.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import vip.imagin.blast.modules.meterial.entity.Caseman;
import vip.imagin.blast.modules.meterial.entity.Marterial;

/**
 * (Marterial-caseMan)表数据库访问层
 *
 * @author 东子
 * @since 2022-03-30 16:52:21
 */
@Mapper
@Repository
public interface CaseManDao extends BaseMapper<Caseman> {


    //TODO 数据库身份证唯一
//    @Select("select casemanid from caseman where identity_number = #{identityNumber}")
    Long selectId(String identityNumber);

    @Select("select count(*) from caseman where identity_number = #{identityNumber}")
    int selectByIdentityNumber(String identityNumber);
}