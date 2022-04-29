package vip.imagin.blast.modules.selectsomething.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import vip.imagin.blast.dto.selectcasemantemp.CaseEnterpriseTempDto;
import vip.imagin.blast.modules.meterial.dao.CaseEnterPriceDao;
import vip.imagin.blast.modules.meterial.entity.Caseenterprice;
import vip.imagin.blast.modules.meterial.entity.Caseman;
import vip.imagin.blast.modules.selectsomething.dao.SelectcasemantempDao;
import vip.imagin.blast.modules.selectsomething.entity.SelectCaseenterpriceTemp;
import vip.imagin.blast.modules.selectsomething.dao.SelectCaseenterpriceTempDao;
import vip.imagin.blast.modules.selectsomething.entity.Selectcasemantemp;
import vip.imagin.blast.modules.selectsomething.service.SelectCaseenterpriceTempService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import vip.imagin.blast.modules.selectsomething.vo.SelectCaseEnterpriseVo;
import vip.imagin.blast.modules.selectsomething.vo.SelectcasemantempVo;
import vip.imagin.blast.utils.Result;
import vip.imagin.blast.utils.Status;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (SelectCaseenterpriceTemp)表服务实现类
 *
 * @author makejava
 * @since 2022-04-26 19:24:41
 */
@Service
public class SelectCaseenterpriceTempServiceImpl extends ServiceImpl<SelectCaseenterpriceTempDao, SelectCaseenterpriceTemp> implements SelectCaseenterpriceTempService {
    @Resource
    private SelectCaseenterpriceTempDao selectCaseenterpriceTempDao;
    @Autowired
    private CaseEnterPriceDao caseEnterPriceDao;

    @Override
    public boolean fillCaseMan(CaseEnterpriseTempDto caseEnterpriseTempDto) {
        //身份证号不存在就不提交
        String caseEnterpriseNumber = caseEnterpriseTempDto.getEnterpriceNumber();
        int count = caseEnterPriceDao.selectByCaseEnterpriseNumber(caseEnterpriseNumber);
        if(count == 0){
            return false;
        }

        //已经申请过就不提交
        LambdaQueryWrapper<SelectCaseenterpriceTemp> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SelectCaseenterpriceTemp::getEnterpriceNumber,caseEnterpriseTempDto.getEnterpriceNumber());
        wrapper.eq(SelectCaseenterpriceTemp::getUserId,caseEnterpriseTempDto.getUserId());
        int counts = selectCaseenterpriceTempDao.selectCount(wrapper);
        if(counts == 0){
            SelectCaseenterpriceTemp selectCaseenterpriceTemp = new SelectCaseenterpriceTemp();
            BeanUtils.copyProperties(caseEnterpriseTempDto,selectCaseenterpriceTemp);
            int reslut = selectCaseenterpriceTempDao.insert(selectCaseenterpriceTemp);
            return reslut>0;
        }
        return false;
    }



    @Override
    public Result selectMyList(Long userid, Page page) {
        LambdaQueryWrapper<SelectCaseenterpriceTemp> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SelectCaseenterpriceTemp::getUserId,userid);
        Page<SelectCaseenterpriceTemp> pages = selectCaseenterpriceTempDao.selectPage(page, wrapper);
        Page<SelectCaseEnterpriseVo> finalpage = new Page<>();
        BeanUtils.copyProperties(pages,finalpage,"records");

        List<SelectCaseenterpriceTemp> records = pages.getRecords();
        List<SelectCaseEnterpriseVo> finalRecords = new ArrayList<>();
        for (SelectCaseenterpriceTemp selectCaseenterpriceTemp : records) {
            Caseenterprice caseenterprice = null;
            //如果通过就把数据查询返回
            if(selectCaseenterpriceTemp.getStatus().equals("1")){
                Long caseenterpriceid = selectCaseenterpriceTemp.getCaseenterpriceid();
                caseenterprice = caseEnterPriceDao.selectById(caseenterpriceid);
            }
            SelectCaseEnterpriseVo caseEnterpriseVo = new SelectCaseEnterpriseVo();
            //通过了返回数据
            if(caseenterprice != null){
                BeanUtils.copyProperties(caseenterprice,caseEnterpriseVo);
            }
            BeanUtils.copyProperties(selectCaseenterpriceTemp,caseEnterpriseVo);
            finalRecords.add(caseEnterpriseVo);
        }
        finalpage.setRecords(finalRecords);
        return new Result(Status.SUCCESS,finalpage);
    }


    @Override
    public boolean updateCaseEnterprise(SelectCaseenterpriceTemp selectCaseenterpriceTemp) {
        //查找selecttemp的status是不是等于1
        //TODO 有bug 不想写了.好像没了
        if("1".equals(selectCaseenterpriceTemp.getStatus())){
            //通过审核，用企业号查询相关数据
            Long caseEnterPriceId = caseEnterPriceDao.selectId(selectCaseenterpriceTemp.getEnterpriceNumber());
            selectCaseenterpriceTemp.setCaseenterpriceid(caseEnterPriceId);
            //写回临时表
            LambdaQueryWrapper<SelectCaseenterpriceTemp> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SelectCaseenterpriceTemp::getId,selectCaseenterpriceTemp.getId());
            //标记为已经审核
            selectCaseenterpriceTemp.setChecked("1");
            int update = selectCaseenterpriceTempDao.update(selectCaseenterpriceTemp,wrapper);
            return update>0;
        }else {
            //审核不通过
            selectCaseenterpriceTemp.setCaseenterpriceid(null);
            LambdaQueryWrapper<SelectCaseenterpriceTemp> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SelectCaseenterpriceTemp::getId, selectCaseenterpriceTemp.getId());
            selectCaseenterpriceTemp.setChecked("1");
            selectCaseenterpriceTempDao.update(selectCaseenterpriceTemp, wrapper);
            return false;
        }
    }
}
