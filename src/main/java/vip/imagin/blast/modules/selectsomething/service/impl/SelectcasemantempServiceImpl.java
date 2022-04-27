package vip.imagin.blast.modules.selectsomething.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vip.imagin.blast.dto.selectcasemantemp.CasemanTempDto;
import vip.imagin.blast.modules.meterial.dao.CaseManDao;
import vip.imagin.blast.modules.meterial.entity.Caseman;
import vip.imagin.blast.modules.selectsomething.dao.SelectcasemantempDao;
import vip.imagin.blast.modules.selectsomething.entity.SelectCaseenterpriceTemp;
import vip.imagin.blast.modules.selectsomething.entity.Selectcasemantemp;
import vip.imagin.blast.modules.selectsomething.service.SelectcasemantempService;
import org.springframework.stereotype.Service;
import vip.imagin.blast.modules.selectsomething.vo.SelectcasemantempVo;
import vip.imagin.blast.utils.Result;
import vip.imagin.blast.utils.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * (Selectcasemantemp)表服务实现类
 *
 * @author makejava
 * @since 2022-04-22 16:56:59
 */
@Service
public class SelectcasemantempServiceImpl extends ServiceImpl<SelectcasemantempDao, Selectcasemantemp> implements SelectcasemantempService {

    @Autowired
    private SelectcasemantempDao selectcasemantempDao;
    //注入casemandao
    @Autowired
    private CaseManDao caseManDao;


    @Override
    public boolean fillCaseMan(CasemanTempDto casemanTempDto) {
        //身份证号不存在就不提交
        String identityNumber = casemanTempDto.getIdentityNumber();
        int count = caseManDao.selectByIdentityNumber(identityNumber);
        if(count == 0){
            return false;
        }

        //已经申请过就不提交
        int casemanid = selectcasemantempDao.selectId(casemanTempDto.getUserId(),identityNumber);
        if(casemanid <= 0){
            Selectcasemantemp selectcasemantemp = new Selectcasemantemp();
            BeanUtils.copyProperties(casemanTempDto,selectcasemantemp);
            int reslut = selectcasemantempDao.insert(selectcasemantemp);
            return reslut>0;
        }
        return false;
    }


    @Override
    public boolean updateCaseManTemp(Selectcasemantemp selectcasemantemp) {
        //查找selecttemp的status是不是等于1
        if("1".equals(selectcasemantemp.getStatus())){
            //通过审核，用身份证查询相关数据
            Long casemanid = caseManDao.selectId(selectcasemantemp.getIdentityNumber());
            selectcasemantemp.setCasemanid(casemanid);
            //写回临时表
            LambdaQueryWrapper<Selectcasemantemp> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Selectcasemantemp::getId,selectcasemantemp.getId());
            //标记为已经审核
            selectcasemantemp.setChecked("1");
            int update = selectcasemantempDao.update(selectcasemantemp,wrapper);
            return update>0;
        }else {
            //审核不通过
            selectcasemantemp.setCasemanid(null);
            LambdaQueryWrapper<Selectcasemantemp> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Selectcasemantemp::getId,selectcasemantemp.getId());
            selectcasemantemp.setChecked("1");
            int update = selectcasemantempDao.update(selectcasemantemp,wrapper);
            return false;
        }
    }

    @Override
    public Result selectMyList(Long userid, Page<Selectcasemantemp> page) {
        //查到自己的申请
        LambdaQueryWrapper<Selectcasemantemp> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Selectcasemantemp::getUserId,userid);
        Page<Selectcasemantemp> pages = selectcasemantempDao.selectPage(page, wrapper);

        //构建返回的分页对象
        Page<SelectcasemantempVo> finalpage = new Page<>();
        BeanUtils.copyProperties(pages,finalpage,"records");
        List<Selectcasemantemp> records = pages.getRecords();
        //把page中的records换了
        List<SelectcasemantempVo> finalRecords = new ArrayList<>();
        for (Selectcasemantemp selectcasemantemp : records) {
            Caseman caseman = null;
            //如果通过就把数据查询返回
            if(selectcasemantemp.getStatus().equals("1")){
                Long casemanid = selectcasemantemp.getCasemanid();
                caseman = caseManDao.selectById(casemanid);
            }
            SelectcasemantempVo selectcasemantempVo = new SelectcasemantempVo();
            //通过了返回数据
            if(caseman != null){
                BeanUtils.copyProperties(caseman,selectcasemantempVo);
            }
            BeanUtils.copyProperties(selectcasemantemp,selectcasemantempVo);
            finalRecords.add(selectcasemantempVo);
        }
        finalpage.setRecords(finalRecords);
        return new Result(Status.SUCCESS,finalpage);
    }
}

