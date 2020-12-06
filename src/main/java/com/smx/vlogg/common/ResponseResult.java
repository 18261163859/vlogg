package com.smx.vlogg.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ResponseResult
 * @Description TODO
 * @Author moses
 * @Date 2020/12/3
 **/
@Data
public class ResponseResult {
    private Integer code;
    private String msg;
    private Object data;

    public ResponseResult() {
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResponseResult success(){
        ResponseResult result=new ResponseResult();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    public static ResponseResult success(Object data){
        ResponseResult result=new ResponseResult();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    public static ResponseResult failure(ResultCode resultCode){
        ResponseResult result=new ResponseResult();
        result.setResultCode(resultCode);
        return result;
    }

    public static ResponseResult failure(ResultCode resultCode,Object data){
        ResponseResult result=new ResponseResult();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }

    public void setResultCode(ResultCode code){
        this.code=code.code();
        this.msg=code.message();
    }
}
