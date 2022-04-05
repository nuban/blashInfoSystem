package vip.imagin.blast.dto.marteriralDto;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import vip.imagin.blast.modules.meterial.entity.Caseenterprice;
import vip.imagin.blast.modules.meterial.entity.Caseman;

import java.io.Serializable;
import java.util.Date;

/**
 * (Marterial)表实体类
 *
 * @author makejava
 * @since 2022-03-30 16:52:21
 */
@Data
public class MarterialDto implements Serializable{
    private static final long serialVersionUID = -40356785423868312L;
    //材料id
    private Long id;
    //上传时间
    private Date date;
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
    //涉爆企业
    private Caseenterprice caseenterprice;
    //涉爆人
    private Caseman caseman;


}

