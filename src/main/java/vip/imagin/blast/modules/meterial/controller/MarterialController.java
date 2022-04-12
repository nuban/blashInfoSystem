package vip.imagin.blast.modules.meterial.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import vip.imagin.blast.dto.marteriralDto.MarterialDto;
import vip.imagin.blast.modules.meterial.entity.Marterial;
import vip.imagin.blast.modules.meterial.service.MarterialService;
import org.springframework.web.bind.annotation.*;
import vip.imagin.blast.modules.user.entity.MyUserDetails;
import vip.imagin.blast.modules.user.entity.User;
import vip.imagin.blast.utils.Result;
import vip.imagin.blast.utils.Status;

import javax.annotation.Resource;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

/**
 * (Marterial)表控制层
 *
 * @author makejava
 * @since 2022-03-30 16:52:21
 */
@RestController
@RequestMapping("material")
@Api(tags = "案件各种操作的接口")
public class MarterialController {
    /**
     * materaerService
     */
    @Resource
    private MarterialService marterialService;

    @ApiOperation(value = "测试")
    @GetMapping("test")
    public Result test(){
        return new Result(Status.SUCCESS);
    }

    @ApiOperation("管理员查询案件列表功能")
    @GetMapping("list")
    @PreAuthorize("hasAuthority('explosive:administer')")
    public Result getList(){
       return marterialService.list();
    }

    @ApiOperation("查看自己上传的案件以及进度,根据传上来的id")
    @GetMapping("mylist")
    @PreAuthorize("hasAuthority('explosive:user')")
//    @ApiImplicitParam(name = "用户的id",value = "用户id",
//            required = true,paramType = "path" /*表示参数放在扫描地方*/)
    public Result getMyList(){
        //从SecurityContextHolder获取id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        Long userId = principal.getUser().getId();
        return marterialService.listMyList(userId);
    }

    @ApiOperation("民警上传案件")
    @PreAuthorize("hasAuthority('explosive:user')")
    @PostMapping("upload")
    public Result uploadMaterial(@RequestBody MarterialDto marterial){
        //TODO 图片上传,userｉｄ设置
        //从SecurityContextHolder获取当前登录用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        Long userid = principal.getUser().getId();
        marterial.setUserId(userid);
        //上传
        boolean result = marterialService.uploadMaterial(marterial);
        if (result) {
            return new Result(Status.SUCCESS);
        }
        return new Result(Status.FAILURE);
    }


}

