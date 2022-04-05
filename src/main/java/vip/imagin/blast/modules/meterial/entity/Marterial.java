package vip.imagin.blast.modules.meterial.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Marterial)表实体类
 *
 * @author makejava
 * @since 2022-03-30 16:52:21
 */
@Data
@TableName("marterial")
public class Marterial implements Serializable{
    private static final long serialVersionUID = -40356785423868312L;
    //材料id
    private Long id;
    //上传时间
    private Date date;
    
    private String time;
    //案件地点
    private String place;
    //报警单位
    private String reporter;
    //案件描述
    private String description;
    //主要的爆炸物
    private String mainExplosive;
    //次要爆炸物
    private String subExplosive;
    //民警id
    private Long userId;
    //是否被审核了
    private Integer examined;
    //案件相关图片
    private String picture;
    //涉爆人
    private String caseman;
    //涉爆企业
    private String caseenterprice;
    //涉爆企业id
    private Long caseenterprceId;
    //涉爆人id
    private Long casemanId;

}

