package vip.imagin.blast.modules.predict.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (Waringvalue)表实体类
 *
 * @author makejava
 * @since 2022-04-22 15:15:38
 */
@SuppressWarnings("serial")
public class Waringvalue extends Model<Waringvalue> {
    
    private Integer id;
    //危险人预估值
    private Integer humanWaringValue;
    //危险地点预估值
    private Integer placeWaringValue;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHumanWaringValue() {
        return humanWaringValue;
    }

    public void setHumanWaringValue(Integer humanWaringValue) {
        this.humanWaringValue = humanWaringValue;
    }

    public Integer getPlaceWaringValue() {
        return placeWaringValue;
    }

    public void setPlaceWaringValue(Integer placeWaringValue) {
        this.placeWaringValue = placeWaringValue;
    }

}

