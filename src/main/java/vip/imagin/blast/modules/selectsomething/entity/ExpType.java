package vip.imagin.blast.modules.selectsomething.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * 爆炸物所属类别
 */
@Data
public class ExpType extends Model<ExpType> {
    
    private Long id;
    //爆炸物类别
    private String typeName;

}

