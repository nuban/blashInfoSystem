package vip.imagin.blast.dto.selectcasemantemp;

import lombok.Data;

/**
 * 隐私查询入参对象
 */
@Data
public class CasemanTempDto {

    //用户id
    private Long userId;
    //根据身份证号码查询
    private String identityNumber;
    //查询理由
    private String selectReason;
}
