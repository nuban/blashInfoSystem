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
@TableName("caseman")
public class Caseenterprice  implements Serializable  {

    private static final long serialVersionUID = -40356785423868312L;
    @TableId/*(type = IdType.NONE)*/
    //涉爆企业
    private Long id;
    //企业名称
    private String name;
    //企业地址
    private String address;
    //研究方向
    private String direction;
}

