package vip.imagin.blast.modules.predict.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import vip.imagin.blast.modules.predict.entity.Waringvalue;
import vip.imagin.blast.modules.predict.service.WaringvalueService;
import org.springframework.web.bind.annotation.*;
import vip.imagin.blast.utils.Result;
import vip.imagin.blast.utils.Status;

import javax.annotation.Resource;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

/**
 * (Waringvalue)表控制层
 *
 * @author makejava
 * @since 2022-04-22 15:15:38
 */
@RestController
@RequestMapping("waringvalue")
@Api(tags = "危险地点，人物预警值的增删改查")
public class WaringvalueController {
    /**
     * 服务对象
     */
    @Resource
    private WaringvalueService waringvalueService;

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    @PreAuthorize("hasAuthority('explosive:user')")
    @ApiOperation(value = "查询")
    public Result selectAll() {
        return new Result(Status.SUCCESS,this.waringvalueService.list());
    }


    /**
     * 修改数据
     *
     * @param waringvalue 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody Waringvalue waringvalue) {
        return new Result(Status.SUCCESS,this.waringvalueService.updateById(waringvalue));
    }

}

