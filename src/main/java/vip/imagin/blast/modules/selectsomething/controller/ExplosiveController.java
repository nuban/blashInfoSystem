package vip.imagin.blast.modules.selectsomething.controller;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import vip.imagin.blast.modules.selectsomething.entity.Explosive;
import vip.imagin.blast.modules.selectsomething.service.ExplosiveService;
import org.springframework.web.bind.annotation.*;
import vip.imagin.blast.utils.Result;
import vip.imagin.blast.utils.Status;

import javax.annotation.Resource;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

/**
 * 爆炸物
 */
@RestController
@Api(tags = "爆炸物相关接口")
@RequestMapping("explosive")
public class ExplosiveController {
    /**
     * 服务对象
     */
    @Resource
    private ExplosiveService explosiveService;

    /**
     * 分页查询搜索数据数据
     *
     * @param page 分页对象
     * @param explosive 查询实体
     * @return 所有数据
     */
    @GetMapping("search")
    @ApiOperation(value = "警员查询爆炸物相关信息")
    @PreAuthorize("hasAuthority('explosive:user')")
    public Result selectAll(Page<Explosive> page, Explosive explosive) {
        LambdaQueryWrapper<Explosive> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Explosive::getName,explosive.getName());
        Page<Explosive> explosivelists = this.explosiveService.page(page, wrapper);
        return new Result(Status.SUCCESS,explosivelists);
    }

    /**
     * 添加爆炸物
     * @param explosive
     * @return
     */
    @PostMapping("add")
    @ApiOperation(value = "管理员添加爆炸物")
    @PreAuthorize("hasAuthority('explosive:admin')")
    public Result insert(@RequestBody Explosive explosive) {
        //传入的爆炸物里面 type 传入typeId，暂时就不分了,图片接口依然复用
        boolean save = this.explosiveService.save(explosive);
        return new Result(save ? Status.SUCCESS: Status.FAIL);
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    @ApiOperation(value = "删除爆炸物,想做不？")
    public Result delete(@RequestParam("idList") List<Long> idList) {
        return new Result(Status.SUCCESS,this.explosiveService.removeByIds(idList));
    }
}

