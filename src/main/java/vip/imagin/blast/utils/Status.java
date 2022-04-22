package vip.imagin.blast.utils;



public enum Status {

    /**
     * 操作成功
     */
    SUCCESS(200,"操作成功"),

    /**
     * 验证码验证失败
     */
    CODE_FAILURE(400,"验证码错误"),

    /**
     * 登录成功
     */
    SUCCESS_LOGIN(200,"登录成功"),

    /**
     * 注销成功
     */
    SUCCESS_LOGOUT(200,"注销成功"),

    /**
     * 注册成功"
     */
    SUCCESS_REGISTRATION(200,"注册成功"),

    /**
     * 操作失败
     */
    FAILURE(500,"操作失败"),

    /**
     * 操作失败,信息不存在
     */
    FAILURE_UPDATE_PASSWORD(500,"操作失败,信息不存在"),

    /**
     * 操作失败
     */
    FAILURE_APPLICATION(500,"操作失败,本学期已申请"),

    /**
     * 操作失败
     */
    FAILURE_SEMESTER_EXIST(500,"操作失败,该学期已存在"),

    /**
     * 图片上传失败
     */
    FAILURE_UPLOAD_IMG(500,"图片上传失败"),


    /**
     * 旧密码错误，修改密码失败
     */
    FAILURE_TO_CHANGE_PASSWORD(500,"修改密码失败，旧密码错误"),

    /**
     * 新密码与旧密码相同
     */
    OLD_PASSWORD_EQULS_NEW(500,"新密码与旧密码相同"),

    /**
     * 参数验证失败
     */
    VALIDATE_FAILED(404,"参数验证失败"),

    /**
     * 登录过期，请重新登录
     */
    LOGIN_DATE(401,"登录过期，请重新登录"),

    /**
     * 注册失败，该用户已存在
     */
    FAILED_REGISTRATION(406,"注册失败，该用户已存在"),

    /**
     * 认证失败请重新登录
     */
    UNAUTHORIZED(401,"认证失败请重新登录"),

    /**
     * 没有操作权限
     */
    FORBIDDEN(403,"没有操作权限"),

    /**
     * token过期,请重新登录
     */
    POSTSUCCESS(200,"提交成功，等待审核"),

    /**
     * redis没打开
     */
    REDIS_ERROR(500,"redis错误"),

    CODE_EMPTY(400,"请输入验证码");


    private Integer code;
    private String msg;

    Status(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }


    public Integer getCode() {
        return this.code;
    }


    public String getMsg() {
        return this.msg;
    }
}

