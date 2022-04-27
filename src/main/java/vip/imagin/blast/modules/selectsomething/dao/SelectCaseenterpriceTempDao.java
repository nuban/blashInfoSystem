package vip.imagin.blast.modules.selectsomething.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import vip.imagin.blast.modules.selectsomething.entity.SelectCaseenterpriceTemp;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (SelectCaseenterpriceTemp)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-26 19:24:39
 */
@Mapper
public interface SelectCaseenterpriceTempDao extends BaseMapper<SelectCaseenterpriceTemp> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SelectCaseenterpriceTemp queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param selectCaseenterpriceTemp 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<SelectCaseenterpriceTemp> queryAllByLimit(SelectCaseenterpriceTemp selectCaseenterpriceTemp, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param selectCaseenterpriceTemp 查询条件
     * @return 总行数
     */
    long count(SelectCaseenterpriceTemp selectCaseenterpriceTemp);

    /**
     * 新增数据
     *
     * @param selectCaseenterpriceTemp 实例对象
     * @return 影响行数
     */
    int insert(SelectCaseenterpriceTemp selectCaseenterpriceTemp);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SelectCaseenterpriceTemp> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SelectCaseenterpriceTemp> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SelectCaseenterpriceTemp> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<SelectCaseenterpriceTemp> entities);

    /**
     * 修改数据
     *
     * @param selectCaseenterpriceTemp 实例对象
     * @return 影响行数
     */
    int update(SelectCaseenterpriceTemp selectCaseenterpriceTemp);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

















}

