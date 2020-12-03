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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseResult {
    private Integer code;
    private String msg;
    private Object data;
}
