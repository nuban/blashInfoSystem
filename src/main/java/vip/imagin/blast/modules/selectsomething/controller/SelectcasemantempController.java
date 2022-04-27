package vip.imagin.blast.modules.selectsomething.controller;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
 * 隐私信息查询
 */

@RestController
@Api(tags = "隐私查询-----涉事人查询")
@RequestMapping("selectcaseman")
public class SelectcasemantempController{
    /**
     * 服务对象
     */
    @Resource
    private SelectcasemantempService selectcasemantempService;

    /**
     * 管理员分页查询所有数据请求
     * @param page 分页对象
     * @return 所有数据
     */
    @GetMapping("list/{id}")
    @ApiOperation(value = "管理员分页查看所有数据请求1代表查看未审核，0表示已经审核 传入分页current size")
    @PreAuthorize("hasAuthority('explosive:admin')")
    public Result selectAll(@PathVariable Integer id,Page<Selectcasemantemp> page) {

        LambdaQueryWrapper<Selectcasemantemp> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Selectcasemantemp::getChecked,id);
        return new Result(Status.SUCCESS, this.selectcasemantempService.page(page,wrapper));
    }

    /**
     * 管理员审核案件
     * 审核的结果
     * @return 所有数据
     */
    @PostMapping("check")
    @ApiOperation(value = "管理员审核通过还是不通过")
    @PreAuthorize("hasAuthority('explosive:admin')")
    public Result changeManTempDto(@RequestBody Selectcasemantemp selectcasemantemp) {
        boolean flag = this.selectcasemantempService.updateCaseManTemp(selectcasemantemp);
        return flag?  new Result(Status.PASS_CASE_TEMP):new Result(Status.NO_PASS);
    }

    /**
     * 查询涉事人
     * @param casemanTempDto
     * @return
     */
    @PreAuthorize("hasAuthority('explosive:user')")
    @ApiOperation(value = "警员提交申请 就传入原因和和身份证号就行了")
    @PostMapping("/postFindCaseMan")
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
     * 查看我的申请列表
     * @return
     */
    @GetMapping("myList")
    @PreAuthorize("hasAuthority('explosive:user')")
    @ApiOperation(value = "警员查询自己申请的状态")
    public Result selectMyList(Page page) {
        //从SecurityContextHolder获取当前登录用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        Long userid = principal.getUser().getId();
        return  selectcasemantempService.selectMyList(userid,page);
    }

}

