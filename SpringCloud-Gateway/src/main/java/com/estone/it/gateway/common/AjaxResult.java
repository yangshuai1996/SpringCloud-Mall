package com.estone.it.gateway.common;

import lombok.Data;

@Data
public class AjaxResult<T> {
    private Integer code;

    private String msg;

    private T data;

    private AjaxResult(){

    }

    private AjaxResult(Integer code,String msg,T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static AjaxResult ok(String msg){
        return new AjaxResult(200,msg,null);
    }

    public static AjaxResult restResult(String msg,Integer code){
        return new AjaxResult(code, msg, null);
    }
}
