package vip.imagin.blast.modules.meterial.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (Caseenterprice)表实体类
 *
 * @author makejava
 * @since 2022-04-04 11:55:33
 */
@Data
@TableName("caseenterprice")
public class Caseenterprice  implements Serializable  {

    @TableId/*(type = IdType.NONE)*/
    //涉爆企业号
    private Long caseenterpriceid;
    //企业名称
    private String name;
    //企业地址
    private String address;
    //研究方向
    private String direction;

}

