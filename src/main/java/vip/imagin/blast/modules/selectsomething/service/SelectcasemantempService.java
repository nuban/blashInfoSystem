package vip.imagin.blast.modules.selectsomething.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.imagin.blast.dto.selectcasemantemp.CasemanTempDto;
import vip.imagin.blast.modules.selectsomething.entity.Selectcasemantemp;

/**
 * (Selectcasemantemp)表服务接口
 *
 * @author makejava
 * @since 2022-04-22 16:56:59
 */
public interface SelectcasemantempService extends IService<Selectcasemantemp> {

    boolean fillCaseMan(CasemanTempDto casemanTempDto);
}

