package com.smx.vlogg.utils;

import java.util.Random;

/**
 * @ClassName StringUtil
 * @Description TODO
 * @Author moses
 * @Date 2020/12/6
 **/
public class StringUtil {

    public static String getVerifyCode(){
        return new Random().nextInt(899999)+100000+"";
    }
}
