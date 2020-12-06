package com.smx.vlogg.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName LoginDto
 * @Description TODO
 * @Author moses
 * @Date 2020/12/5
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDto {
    private String phone;
    private String password;
}
