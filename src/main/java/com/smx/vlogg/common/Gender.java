package com.smx.vlogg.common;

/**
 * @ClassName Gender
 * @Description TODO
 * @Author moses
 * @Date 2020/12/5
 **/
public enum Gender {
    /**
     * 性别枚举
     */
    male(1,"男"),
    female(2,"女"),
    secret(0,"保密");

    public final Integer type;
    public final String value;

    Gender(Integer type,String value){
        this.type=type;
        this.value=value;
    }
}
