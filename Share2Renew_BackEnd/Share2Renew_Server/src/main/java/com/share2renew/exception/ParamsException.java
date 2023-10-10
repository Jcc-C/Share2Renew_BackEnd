package com.share2renew.exception;

/**
 * @program: Share2Renew_BackEnd
 * @description:
 * @author: Junxian Cai
 **/
public class ParamsException extends Exception{
    private Integer code=300;
    private String msg="Data exceptions!";


    public ParamsException() {
        super("Data exceptions!");
    }

    public ParamsException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ParamsException(Integer code) {
        super("Data exceptions!");
        this.code = code;
    }

    public ParamsException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
