package vip.imagin.blast.modules.selectsomething.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import vip.imagin.blast.modules.selectsomething.entity.ExpType;
import vip.imagin.blast.modules.selectsomething.service.ExpTypeService;
import org.springframework.web.bind.annotation.*;
import vip.imagin.blast.utils.Result;
import vip.imagin.blast.utils.Status;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (ExpType)表控制层
 *
 * @author makejava
 * @since 2022-05-01 22:01:42
 */
@RestController
@Api(tags = "查询下拉列表")
@RequestMapping("expType")
public class ExpTypeController {
    /**
     * 服务对象
     */
    @Resource
    private ExpTypeService expTypeService;

    /**
     * 列出分类列表
     * @return
     */
    @GetMapping
    @ApiOperation(value = "下拉列表查询")
    @PreAuthorize("hasAuthority('explosive:admin')")
    public Result selectAll() {
        //TODO 后期迭代的时候再增加
        return new Result(Status.SUCCESS,this.expTypeService.list());
    }

}

