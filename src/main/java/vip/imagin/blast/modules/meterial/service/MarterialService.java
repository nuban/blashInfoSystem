package vip.imagin.blast.modules.meterial.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.imagin.blast.dto.marteriralDto.MarterialCheckDto;
import vip.imagin.blast.dto.marteriralDto.MarterialDto;
import vip.imagin.blast.modules.meterial.entity.Marterial;
import vip.imagin.blast.utils.Result;

/**
 * (Marterial)表服务接口
 *
 * @author makejava
 * @since 2022-03-30 16:52:21
 */
public interface MarterialService {

    Result list(Integer id, Page<Marterial> page);

    Result listMyList(Long id);

    boolean uploadMaterial(MarterialDto marterial);

    Result searchPlace(String description);

    Result searchprecise(String[] strings);

    Result checklist(MarterialCheckDto result);
}

