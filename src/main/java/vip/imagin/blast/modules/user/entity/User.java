package vip.imagin.blast.modules.user.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = -40353785423868312L;
    //用户id
    @TableId
    private Long id;
    
    private String username;
    
    private String password;
    
    private String nickname;
    
    private String avatar;
    
    private String status;
    //是否删除
    private String delflag;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createtime;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatetime;
    //性别
    private String gender;
    //注册的注册码
    private String signcode;
    //用户的类型 "1"表示管理员 “0”表示普通用户
    private String usertype;

    public User(String username, String password,String signcode) {
        this.username = username;
        this.password = password;
        this.signcode = signcode;
    }
}

