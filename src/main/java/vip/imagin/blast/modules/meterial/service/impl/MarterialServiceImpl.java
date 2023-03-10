package vip.imagin.blast.modules.meterial.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import vip.imagin.blast.dto.marteriralDto.MarterialCheckDto;
import vip.imagin.blast.dto.marteriralDto.MarterialDto;
import vip.imagin.blast.modules.meterial.dao.CaseEnterPriceDao;
import vip.imagin.blast.modules.meterial.dao.CaseManDao;
import vip.imagin.blast.modules.meterial.dao.MarterialDao;
import vip.imagin.blast.modules.meterial.entity.Caseenterprice;
import vip.imagin.blast.modules.meterial.entity.Caseman;
import vip.imagin.blast.modules.meterial.entity.Marterial;
import vip.imagin.blast.modules.meterial.service.MarterialService;
import org.springframework.stereotype.Service;
import vip.imagin.blast.utils.Result;
import vip.imagin.blast.utils.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * (Marterial)表服务实现类
 *
 * @author makejava
 * @since 2022-03-30 16:52:22
 */
@Slf4j
@Service("marterialService")
public class MarterialServiceImpl implements MarterialService {

    @Autowired
    private CaseEnterPriceDao caseEnterPriceDao;
    @Autowired
    private CaseManDao caseManDao;
    @Autowired
    private MarterialDao materialDao;

    /**
     * 管理员查询所有案件
     *
     * @return
     */
    @Override
    public Result list(Integer id, Page<Marterial> page) {
        LambdaQueryWrapper<Marterial> wrapper = new LambdaQueryWrapper<>();
        //'1'表示查询已经审核
        if(id == 1){
            log.info("管理员查看已经审核的列表");
            wrapper.eq(Marterial::getExamined,"1");
        }else {
            log.info("管理员查看未审核列表");
            wrapper.eq(Marterial::getExamined,"0");
        }
        return new Result(Status.SUCCESS, materialDao.selectPage(page,wrapper));
    }

    /**
     * 根据id 查询自己上传的案件
     *
     * @param id
     * @return
     */
    @Override
    public Result listMyList(Long id) {
        List<Marterial> marterial = materialDao.findMarterialByuserid(id);
        return new Result(Status.SUCCESS, marterial);
    }

    /**
     * 民警上传案件
     *
     * @param marterial
     * @return
     */
    /**
     * 加到事物
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean uploadMaterial(MarterialDto marterial) {
        //TODO 插入数据发生异常 结果封装
        //真正写入数据库的对象
        Marterial finalmarterial = new Marterial();
        //拷贝属性
        BeanUtils.copyProperties(marterial, finalmarterial);
        //添加涉事企业
        Caseenterprice caseenterprice = marterial.getCaseenterprice();
        if (!Objects.isNull(caseenterprice)) {
            //TODO
            //Insert语句返回插入的id，注：insert会把查到的数据回写到实体类中  数据要先插入再设置
            caseEnterPriceDao.insertEnterprice(caseenterprice);
            //设置到marterial表对应的实体类中
            finalmarterial.setCaseenterprceId(caseenterprice.getCaseenterpriceid());

        } else {
            log.info("caseenterprice为空");
            //为空设置一个0
            finalmarterial.setCaseenterprceId(null);
        }
        //添加涉事人
        Caseman caseMan = marterial.getCaseman();
        if (!Objects.isNull(caseMan)) {
            caseManDao.insert(caseMan);
            finalmarterial.setCasemanId(caseMan.getCasemanid());
        } else {
            log.info("caseman为空");
            //为空设置一个0
            finalmarterial.setCasemanId(null);
        }
        //添加主案件
        materialDao.insert(finalmarterial);

        return true;
    }

    /**
     * 搜索现场描述
     *
     * @param description
     * @return
     */
    @Override
    public Result searchPlace(String description) {
        LambdaQueryWrapper<Marterial> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!description.isEmpty(), Marterial::getPlaceDescription, description);
        List<Marterial> marterials = materialDao.selectList(queryWrapper);
        return new Result(Status.SUCCESS, marterials);
    }

    /**
     * 精确查询
     *
     * @param strings
     * @return
     */
    @Override
    public Result searchprecise(String[] strings) {
        LambdaQueryWrapper<Marterial> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.and(ilqw -> {
            for (String string : strings) {
                ilqw.or(iilqw -> iilqw.like(null != string, Marterial::getPlaceDescription, string));
            }
        });

        List<Marterial> marterials = materialDao.selectList(queryWrapper);
        return new Result(Status.SUCCESS, marterials);
    }


    @Override
    public Result checklist(MarterialCheckDto result) {
        //审核通过，更改数据
        if("1".equals(result.getPassed())){
            materialDao.updateByMarterial1Id(result.getId());
            return  new Result(Status.PASS_CASE_TEMP);
        }else {
            //审核不通过
            materialDao.updateByMarterialId(result.getId(),result.getNoPassReason());
            return new Result(Status.GO_AWAY);
        }
    }
}

