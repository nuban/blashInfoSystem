package vip.imagin.blast.modules.meterial.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (Caseman)表实体类
 *
 * @author makejava
 * @since 2022-04-04 11:51:02
 */
@Data
@TableName("caseman")
public class Caseman  implements Serializable {

    //涉爆人员身份证
    @TableId
    private Long casemanid;
    //涉爆人员姓名
    private String name;
    //涉爆人的电话
    private String phonenumber;
    //涉爆人员的公司
    private String firm;
}

