package com.tihom.manager.error;

/**
 * 错误种类
 * @author TiHom
 * create at 2018/8/2 0002.
 */
public enum  ErrorEnum {

    ID_NOT_NULL("F001","编号不可为空",false),
    //...
    UNKOWN("999","未知异常",false);

    private String code;
    private String message;
    private boolean canRetry; //是否可重试

    ErrorEnum(String code, String message, boolean canRetry) {
        this.code = code;
        this.message = message;
        this.canRetry = canRetry;
    }

    public static ErrorEnum getByCode(String code){
        for (ErrorEnum errorEnum : ErrorEnum.values()) {
            if(errorEnum.code.equals(code)){
                return errorEnum;
            }
        }
        return UNKOWN;
    }

    public boolean isCanRetry() {
        return canRetry;
    }

    public void setCanRetry(boolean canRetry) {
        this.canRetry = canRetry;
    }

    public String getCode() {

        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
