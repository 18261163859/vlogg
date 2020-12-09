package com.smx.vlogg.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Feedback
 * @Description TODO
 * @Author moses
 * @Date 2020/12/8
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Feedback {
    private Integer id;
    private String title;
    private String content;
    private String phone;
}
