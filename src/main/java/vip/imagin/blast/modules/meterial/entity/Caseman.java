package vip.imagin.blast.modules.meterial.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Caseman)表实体类
 *
 * @author makejava
 * @since 2022-04-04 11:51:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("caseman")
public class Caseman  implements Serializable {

    /**
     * 这里改了实体类，对应的vo也要记住改下
     */
    //涉爆人员身份证
    @TableId(type = IdType.NONE)
    private Long casemanid;
    //涉爆人员姓名
    private String name;
    private String birthday;
    private String identityNumber;
    private String address;
    private String picture;
    private String gender;
    private String phonenumber;
    private String firm;
}

