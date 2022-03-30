package vip.imagin.blast.modules.meterial.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import vip.imagin.blast.modules.meterial.entity.Marterial;
import vip.imagin.blast.modules.meterial.service.MarterialService;
import org.springframework.web.bind.annotation.*;
import vip.imagin.blast.modules.user.entity.User;
import vip.imagin.blast.utils.Result;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Marterial)表控制层
 *
 * @author makejava
 * @since 2022-03-30 16:52:21
 */
@RestController
@RequestMapping("material")
public class MarterialController {
    /**
     * 服务对象
     */
    @Resource
    private MarterialService marterialService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param marterial 查询实体
     * @return 所有数据
     */
    @GetMapping("test")
    public String  list(Page<User> page, Marterial marterial) {
        return "hello";
    }
}

