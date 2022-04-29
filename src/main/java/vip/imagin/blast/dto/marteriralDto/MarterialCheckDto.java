package vip.imagin.blast.dto.marteriralDto;

import lombok.Data;

/**
 * 案件审核的dto
 */
@Data
public class MarterialCheckDto {
    //案件id
    private Long id;
    //是否通过
    private String passed;
    //不通过原因
    private String noPassReason;

}
