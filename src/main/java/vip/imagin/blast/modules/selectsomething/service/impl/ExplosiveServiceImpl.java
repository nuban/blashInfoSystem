package vip.imagin.blast.modules.selectsomething.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import vip.imagin.blast.modules.selectsomething.dao.ExplosiveDao;
import vip.imagin.blast.modules.selectsomething.entity.Explosive;
import vip.imagin.blast.modules.selectsomething.service.ExplosiveService;
import org.springframework.stereotype.Service;

/**
 * (Explosive)表服务实现类
 *
 * @author makejava
 * @since 2022-05-01 21:49:18
 */
@Service("explosiveService")
public class ExplosiveServiceImpl extends ServiceImpl<ExplosiveDao, Explosive> implements ExplosiveService {

}

