package com.smx.vlogg.utils;

/**
 * @ClassName DataUtil
 * @Description TODO
 * @Author moses
 * @Date 2020/12/3
 **/

import com.smx.vlogg.model.Card;

import java.util.Arrays;
import java.util.List;
public class DataUtil {
    public static List<Card> initCards() {
        Card[] cards = new Card[]{
                Card.builder()
                        .id(1)
                        .title("Java学习")
                        .bgImg("https://s-moses.oss-cn-hangzhou.aliyuncs.com/logo/0873F308-5997-48EA-B2BB-FFFDF254163B.jpeg")
                        .content("Java学习")
                        .build(),
                Card.builder()
                        .id(1)
                        .title("Java Script学习")
                        .bgImg("https://s-moses.oss-cn-hangzhou.aliyuncs.com/logo/1D0C6F4D-D724-4788-8E95-FEE5E9869FFB.jpeg")
                        .content("Java Script学习")
                        .build(),
                Card.builder()
                        .id(1)
                        .title("Linux学习")
                        .bgImg("https://s-moses.oss-cn-hangzhou.aliyuncs.com/logo/2919fa8e-ea4c-42c3-b7ff-2c0fbcb8f859.jpg")
                        .content("Linux学习")
                        .build(),
                Card.builder()
                        .id(1)
                        .title("MySQL学习")
                        .bgImg("https://s-moses.oss-cn-hangzhou.aliyuncs.com/logo/3F9C1953-8D18-4559-8510-B117A8FDF746.jpeg")
                        .content("SpringCloud学习")
                        .build(),
                Card.builder()
                        .id(1)
                        .title("Python学习")
                        .bgImg("https://s-moses.oss-cn-hangzhou.aliyuncs.com/logo/9A050805-1834-4915-AE1E-8E20941E5882.jpeg")
                        .content("SpringCloud学习")
                        .build()
        };
        return Arrays.asList(cards);
    }
}
