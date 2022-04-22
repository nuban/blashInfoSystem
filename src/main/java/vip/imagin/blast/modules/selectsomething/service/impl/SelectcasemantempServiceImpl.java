package vip.imagin.blast.modules.selectsomething.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import vip.imagin.blast.dto.selectcasemantemp.CasemanTempDto;
import vip.imagin.blast.modules.selectsomething.dao.SelectcasemantempDao;
import vip.imagin.blast.modules.selectsomething.entity.Selectcasemantemp;
import vip.imagin.blast.modules.selectsomething.service.SelectcasemantempService;
import org.springframework.stereotype.Service;

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
    @Override
    public boolean fillCaseMan(CasemanTempDto casemanTempDto) {
        Selectcasemantemp selectcasemantemp = new Selectcasemantemp();
        BeanUtils.copyProperties(casemanTempDto,selectcasemantemp);
        int reslut = selectcasemantempDao.insert(selectcasemantemp);
        return reslut>0;
    }
}

