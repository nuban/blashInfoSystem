package vip.imagin.blast.dto.selectcasemantemp;

import lombok.Data;

/**
 * 隐私查询入参对象
 */
@Data
public class CaseEnterpriseTempDto {

    //用户id
    private Long userId;
    //根据企业证号码查询
    private String enterpriceNumber;
    //查询理由
    private String selectReason;
}
