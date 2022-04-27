package vip.imagin.blast.modules.meterial.controller;



import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import vip.imagin.blast.dto.marteriralDto.MarterialDto;
import vip.imagin.blast.modules.meterial.entity.Marterial;
import vip.imagin.blast.modules.meterial.service.MarterialService;
import org.springframework.web.bind.annotation.*;
import vip.imagin.blast.modules.user.entity.MyUserDetails;
import vip.imagin.blast.modules.user.entity.User;
import vip.imagin.blast.utils.Result;
import vip.imagin.blast.utils.Status;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * (Marterial)表控制层
 *
 * @author makejava
 * @since 2022-03-30 16:52:21
 */
@RestController
@RequestMapping("material")
@Slf4j
@Api(tags = "案件各种操作的接口")
public class MarterialController {

    @Value("${img.path}")
    private String filePath;

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

    @ApiOperation("查看当前登录用户上传的案件以及进度")
    @GetMapping("mylist")
    @PreAuthorize("hasAuthority('explosive:user')")
    public Result getMyList(){
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        Long userId = principal.getUser().getId();
        return marterialService.listMyList(userId);
    }

    @ApiOperation("输入现场描述查询")
    @GetMapping("searchByPlace")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "description",value = "string "
            , required = true,paramType = "path" /*表示参数放在扫描地方*/)
    })
    @PreAuthorize("hasAuthority('explosive:user')")
    public Result getPlace(String description){
        return marterialService.searchPlace(description);
    }


    @ApiOperation("精确查询(python分词部分)")
    @GetMapping("searchPrecision")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "descriptions",value = "string "
            , required = true,paramType = "path" /*表示参数放在扫描地方*/)
    })
    @PreAuthorize("hasAuthority('explosive:user')")
    public Result getPricePlace( String descriptions){
        //分词
        List<String> list = new ArrayList<String>();
        Process proc;
        try {
            proc = Runtime.getRuntime().exec(new String[] {"python","E:\\java\\JavaSeLearn\\第八章\\src\\python\\mian.py" ,descriptions});// 执行py文件
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"GBK"));
            String line = null;
            String temp = null;
            while ((temp = in.readLine()) != null) {
                line = temp;
                String substring = line.substring(1, line.length() - 1);
                String[] split = substring.split("\'");
                for (int i = 0 ;i < split.length;i++) {
                    if(i%2 == 1){
                        list.add(split[i]);
                    }
                }
            }
            log.info("分词结果：[]",list);
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String[] strings = list.toArray(new String[list.size()]);

        return marterialService.searchprecise(strings);

    }

    @ApiOperation("民警上传案件")
    @PreAuthorize("hasAuthority('explosive:user')")
    @PostMapping("upload")
    public Result uploadMaterial(@RequestBody MarterialDto marterial){
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

    @ApiOperation("上传图片的接口，上传之后会返回一个文件名")
    @PreAuthorize("hasAuthority('explosive:user')")
    @PostMapping("imgupload")
    public Result uploadImage(MultipartFile file){
        //得到原本的名字
        String originalFilename = file.getOriginalFilename();
        //截取后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String prename = originalFilename.substring(0,originalFilename.lastIndexOf("."));
        //拼凑文件名,防止重名
        String fullName = prename+ UUID.randomUUID().toString()+suffix;

        File dir = new File(filePath);
        if(!dir.exists()){
            dir.mkdirs();
        }

        try {
            file.transferTo(new File(filePath+fullName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Result(Status.SUCCESS,fullName);
    }

    @ApiOperation("图片预览的接口")
    @PreAuthorize("hasAuthority('explosive:user')")
    @GetMapping("download")
    public void download(String filename, HttpServletResponse response){

        try {
            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(filePath + filename));

            //输出流，通过输出流将文件写回浏览器
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            //使用hutools直接copy
            IoUtil.copy(fileInputStream, outputStream);
            outputStream.close();
            fileInputStream.close();
           // return new Result(Status.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

