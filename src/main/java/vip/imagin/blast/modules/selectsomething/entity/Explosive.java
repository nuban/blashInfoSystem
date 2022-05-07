package vip.imagin.blast.modules.selectsomething.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * 爆炸物实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Explosive extends Model<Explosive> {
    //易改生活用品
    private Long id;
    //爆炸物名字
    private String name;
    //爆炸物图片
    private String picture;
    //爆炸物描述
    private String description;
    //类别id
    private Long typeId;
    //起爆器材
    private String startMerterial;
    //易改生活用品
    private String easyLife;

    }

