package vip.imagin.blast.modules.meterial.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import vip.imagin.blast.modules.meterial.entity.Caseenterprice;

import javax.xml.transform.Source;


/**
 * (Marterial-caseMan)表数据库访问层
 * @author 东子
 * @since 2022-03-30 16:52:21
 */
@Mapper
@Repository
public interface CaseEnterPriceDao extends BaseMapper<Caseenterprice>{

    void insertEnterprice(Caseenterprice caseenterprice);

}
