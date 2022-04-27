package vip.imagin.blast.modules.selectsomething.vo;

import lombok.Data;

/**
 * 返回给用户的企业隐私查询实体
 */
@Data
public class SelectCaseEnterpriseVo {

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

    //企业名称
    private String name;
    //企业地址
    private String address;
    //研究方向
    private String direction;

}
