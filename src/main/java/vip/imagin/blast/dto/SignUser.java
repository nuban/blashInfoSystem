package vip.imagin.blast.dto;

import lombok.Data;

/**
 * 注册dto
 */
@Data
public class SignUser {
    private String userName;
    private String password;
    //注册码
    private String signCode;
    private String sureCode;
}
