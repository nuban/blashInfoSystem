package vip.imagin.blast.modules.selectsomething.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.imagin.blast.dto.selectcasemantemp.CaseEnterpriseTempDto;
import vip.imagin.blast.modules.selectsomething.entity.SelectCaseenterpriceTemp;
import org.springframework.data.domain.PageRequest;
import vip.imagin.blast.modules.selectsomething.entity.Selectcasemantemp;
import vip.imagin.blast.utils.Result;

/**
 * (SelectCaseenterpriceTemp)表服务接口
 *
 * @author makejava
 * @since 2022-04-26 19:24:41
 */
public interface SelectCaseenterpriceTempService extends IService<SelectCaseenterpriceTemp> {

    boolean fillCaseMan(CaseEnterpriseTempDto caseEnterpriseTempDto);

    Result selectMyList(Long userid, Page page);

    boolean updateCaseEnterprise(SelectCaseenterpriceTemp selectCaseenterpriceTemp);
}
