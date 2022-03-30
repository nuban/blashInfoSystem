package vip.imagin.blast.modules.user.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Menu)表实体类
 *
 * @author makejava
 * @since 2022-03-30 17:38:48
 */

@Data
@TableName("menu")
public class Menu implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;
    //权限id
    private Long id;
    //权限标识
    private String status;
    //菜单图标
    private String permission;
    
    private Long createby;
    
    private Date createtime;
    //是否删除
    private String delflag;
    //备注
    private String remark;

}

