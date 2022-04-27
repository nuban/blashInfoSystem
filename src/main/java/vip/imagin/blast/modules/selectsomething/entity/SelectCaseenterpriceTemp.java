package vip.imagin.blast.modules.selectsomething.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 隐私查询企业实体
 */
@Data
public class SelectCaseenterpriceTemp implements Serializable {
    private static final long serialVersionUID = -29984769603696407L;

    private Integer id;

    private Long userId;

    /**
     * 企业号根据这个查
     */
    private String enterpriceNumber;
    /**
     * 查询理由
     */
    private String selectReason;
    /**
     * 是否审批通过
     */
    private String status;
    /**
     * 驳回理由
     */
    private String noReason;
    /**
     * 涉事企业id
     */
    private Long caseenterpriceid;
    /**
     * 是否审核过
     */
    private String checked;


}

