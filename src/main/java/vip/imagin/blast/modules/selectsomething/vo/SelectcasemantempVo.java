package vip.imagin.blast.modules.selectsomething.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import vip.imagin.blast.modules.meterial.entity.Caseman;

/**
 * 返回给用户的隐私查询实体
 */
@Data
public class SelectcasemantempVo {
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

    private String checked;

    private String name;
    private String birthday;
    private String address;
    private String picture;
    private String gender;
    private String phonenumber;
    private String firm;
}
