package vip.imagin.blast.modules.selectsomething.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (Selectcasemantemp)表实体类
 *
 * @author makejava
 * @since 2022-04-22 16:56:59
 */
@SuppressWarnings("serial")
@Data
public class Selectcasemantemp extends Model<Selectcasemantemp> {
    
    private Integer id;
    //用户id
    private Long userId;
    //根据身份证号码查询
    private String identityNumber;
    //查询理由
    private String selectReason;
    //是否审批通过
    private String status;
    //驳回理由
    private String noReason;
    //涉事人id
    private Long casemanid;

}

