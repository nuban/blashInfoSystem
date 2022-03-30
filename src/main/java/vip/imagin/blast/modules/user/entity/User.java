package vip.imagin.blast.modules.user.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (User)表实体类
 *
 * @author 东子
 * @since 2022-03-30 16:16:24
 */
@Data
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = -40356785423868312L;
    //用户id
    private Long id;
    
    private String username;
    
    private String password;
    
    private String nickname;
    
    private String avatar;
    
    private String status;
    //是否删除
    private String delflag;
    //创建时间
    private Date createtime;
    //删除时间
    private Date updatetime;
    //性别
    private String gender;

}

