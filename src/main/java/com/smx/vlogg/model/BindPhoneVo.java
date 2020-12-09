package com.smx.vlogg.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName BindPhoneVo
 * @Description TODO
 * @Author moses
 * @Date 2020/12/8
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BindPhoneVo {
    private Integer id;
    private String phone;
    private String code;
    private String vxOpenId;
}
