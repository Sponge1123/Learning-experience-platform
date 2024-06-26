package com.buka.util;

import com.buka.enums.AppHttpCodeEnum;
import lombok.Data;

import java.io.Serializable;
@Data
public class ResponseResult implements Serializable {
    private Integer code;
    private String msg;
    private Object data;

    public ResponseResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult() {
    }
    public static ResponseResult ok(){
        return new ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(),AppHttpCodeEnum.SUCCESS.getMsg(), null);
    }
    public static ResponseResult ok(Object data){
        return new ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(),AppHttpCodeEnum.SUCCESS.getMsg(), data);
    }
    public static ResponseResult getResponseResult(AppHttpCodeEnum appHttpCodeEnum){
        return new ResponseResult(appHttpCodeEnum.getCode(), appHttpCodeEnum.getMsg(), null);
    }
    public static ResponseResult getResponseResult(AppHttpCodeEnum appHttpCodeEnum,Object data){
        return new ResponseResult(appHttpCodeEnum.getCode(), appHttpCodeEnum.getMsg(), data);
    }
    public static ResponseResult errorResult(AppHttpCodeEnum appHttpCodeEnum){
        return new ResponseResult(appHttpCodeEnum.getCode(), appHttpCodeEnum.getMsg(), null);
    }
}
