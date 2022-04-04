package vip.imagin.blast.dto;

import lombok.Data;

/**
 * 登录的dto类
 */
@Data
public class LoginUser {
    private String userName;
    private String password;
    private String sureCode;
    //获取验证码
    private String   verifyKey;
}
