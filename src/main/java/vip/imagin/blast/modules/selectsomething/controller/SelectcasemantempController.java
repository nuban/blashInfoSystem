package vip.imagin.blast.modules.selectsomething.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import vip.imagin.blast.dto.selectcasemantemp.CasemanTempDto;
import vip.imagin.blast.modules.selectsomething.entity.Selectcasemantemp;
import vip.imagin.blast.modules.selectsomething.service.SelectcasemantempService;
import org.springframework.web.bind.annotation.*;
import vip.imagin.blast.modules.user.entity.MyUserDetails;
import vip.imagin.blast.utils.Result;
import vip.imagin.blast.utils.Status;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
/**
 * 查询隐私信息审核
 */

@RestController
@Api(tags = "隐私查询")
@RequestMapping("selectcaseman")
public class SelectcasemantempController{
    /**
     * 服务对象
     */
    @Resource
    private SelectcasemantempService selectcasemantempService;

    /**
     * 管理员  分页查询所有数据
     * @param page 分页对象
     * @return 所有数据
     */
    @GetMapping("list")
    @ApiOperation(value = "传入分页 current size")
    @PreAuthorize("hasAuthority('explosive:admin')")
    public Result selectAll(Page<Selectcasemantemp> page) {
        return new Result(Status.SUCCESS, this.selectcasemantempService.page(page));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return  new Result(Status.SUCCESS, this.selectcasemantempService.getById(id));
    }

    /**
     * 查询涉事人
     * @param casemanTempDto
     * @return
     */
    @PreAuthorize("hasAuthority('explosive:user')")
    @ApiOperation(value = "就传入原因和和身份证号就行了")
    @PostMapping("/findcaseman")
    public Result postOne(@RequestBody CasemanTempDto casemanTempDto) {
        //从SecurityContextHolder获取当前登录用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        Long userid = principal.getUser().getId();
        casemanTempDto.setUserId(userid);
        boolean result =  selectcasemantempService.fillCaseMan(casemanTempDto);
        if(result){
            return  new Result(Status.POSTSUCCESS);
        }
        return  new Result(Status.FAILURE);
    }
    /**
     * 新增数据
     *
     * @param selectcasemantemp 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result insert(@RequestBody Selectcasemantemp selectcasemantemp) {
        return  new Result(Status.SUCCESS, this.selectcasemantempService.save(selectcasemantemp));
    }

    /**
     * 修改数据
     *
     * @param selectcasemantemp 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody Selectcasemantemp selectcasemantemp) {
        return  new Result(Status.SUCCESS, this.selectcasemantempService.updateById(selectcasemantemp));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result delete(@RequestParam("idList") List<Long> idList) {
        return  new Result(Status.SUCCESS, this.selectcasemantempService.removeByIds(idList));
    }
}

