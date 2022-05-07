package vip.imagin.blast.modules.selectsomething.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import vip.imagin.blast.modules.selectsomething.dao.ExpTypeDao;
import vip.imagin.blast.modules.selectsomething.entity.ExpType;
import vip.imagin.blast.modules.selectsomething.service.ExpTypeService;
import org.springframework.stereotype.Service;

/**
 * (ExpType)表服务实现类
 *
 * @author makejava
 * @since 2022-05-01 22:01:42
 */
@Service("expTypeService")
public class ExpTypeServiceImpl extends ServiceImpl<ExpTypeDao, ExpType> implements ExpTypeService {

}

