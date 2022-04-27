package vip.imagin.blast.modules.selectsomething.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import vip.imagin.blast.dto.selectcasemantemp.CaseEnterpriseTempDto;
import vip.imagin.blast.dto.selectcasemantemp.CasemanTempDto;
import vip.imagin.blast.modules.selectsomething.entity.SelectCaseenterpriceTemp;
import vip.imagin.blast.modules.selectsomething.entity.Selectcasemantemp;
import vip.imagin.blast.modules.selectsomething.service.SelectCaseenterpriceTempService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vip.imagin.blast.modules.user.entity.MyUserDetails;
import vip.imagin.blast.utils.Result;
import vip.imagin.blast.utils.Status;

import javax.annotation.Resource;

/**
 * (SelectCaseenterpriceTemp)表控制层
 *
 * @author makejava
 * @since 2022-04-26 19:24:39
 */
@RestController
@Api(tags = "隐私查询-----涉事企业查询")
@RequestMapping("selectCaseEnterprise")
public class SelectCaseenterpriceTempController {
    /**
     * 服务对象
     */
    @Resource
    private SelectCaseenterpriceTempService selectCaseenterpriceTempService;

    @PreAuthorize("hasAuthority('explosive:user')")
    @ApiOperation(value = "警员提交申请 就传入原因和和企业证号就行了")
    @PostMapping("postFindCaseEnterprise")
    public Result postOne(@RequestBody CaseEnterpriseTempDto caseEnterpriseTempDto) {
        //从SecurityContextHolder获取当前登录用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        Long userid = principal.getUser().getId();
        caseEnterpriseTempDto.setUserId(userid);
        boolean result =  selectCaseenterpriceTempService.fillCaseMan(caseEnterpriseTempDto);
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
    @ApiOperation(value = "警员查询自己申请的状态---企业查询")
    public Result selectMyList(com.baomidou.mybatisplus.extension.plugins.pagination.Page page) {
        //从SecurityContextHolder获取当前登录用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        Long userid = principal.getUser().getId();
        return  selectCaseenterpriceTempService.selectMyList(userid,page);
    }


    @GetMapping("list/{id}")
    @ApiOperation(value = "管理员分页查看所有数据请求0代表查看未审核，1表示已经审核 传入分页current size")
    @PreAuthorize("hasAuthority('explosive:admin')")
    public Result selectAll(@PathVariable Integer id, Page<SelectCaseenterpriceTemp> page) {

        LambdaQueryWrapper<SelectCaseenterpriceTemp> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SelectCaseenterpriceTemp::getChecked,id);
        return new Result(Status.SUCCESS, selectCaseenterpriceTempService.page(page,wrapper));
    }

    /**
     * 管理员审核案件
     * 审核的结果
     * @return 所有数据
     */
    @PostMapping("check")
    @ApiOperation(value = "管理员审核通过还是不通过")
    @PreAuthorize("hasAuthority('explosive:admin')")
    public Result changeManTempDto(@RequestBody SelectCaseenterpriceTemp selectCaseenterpriceTemp) {
        boolean flag = this.selectCaseenterpriceTempService.updateCaseEnterprise(selectCaseenterpriceTemp);
        return flag?  new Result(Status.PASS_CASE_TEMP):new Result(Status.NO_PASS);
    }
}

