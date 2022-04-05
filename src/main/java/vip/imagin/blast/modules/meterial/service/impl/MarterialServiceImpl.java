package vip.imagin.blast.modules.meterial.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import vip.imagin.blast.dto.marteriralDto.MarterialDto;
import vip.imagin.blast.modules.meterial.dao.MarterialDao;
import vip.imagin.blast.modules.meterial.service.MarterialService;
import org.springframework.stereotype.Service;
import vip.imagin.blast.utils.Result;
import vip.imagin.blast.utils.Status;

/**
 * (Marterial)表服务实现类
 *
 * @author makejava
 * @since 2022-03-30 16:52:22
 */
@Service("marterialService")
public class MarterialServiceImpl implements MarterialService {


    @Autowired
    private MarterialDao materialDao;

    /**
     * 管理员查询所有案件
     * @return
     */
    @Override
    public Result list() {
        return new Result(Status.SUCCESS,materialDao.selectList(null)) ;
    }

    /**
     * 根据id 查询自己上传的案件
     * @param id
     * @return
     */
    @Override
    public Result listMyList(Long id) {
        return null;
    }

    /**
     * 民警上传案件
     * @param marterial
     * @return
     */
    @Override
    public boolean uploadMaterial(MarterialDto marterial) {
        return false;
    }
}

