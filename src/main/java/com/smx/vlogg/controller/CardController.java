package com.smx.vlogg.controller;

import com.smx.vlogg.common.ResponseResult;
import com.smx.vlogg.common.ResultCode;
import com.smx.vlogg.model.Card;
import com.smx.vlogg.utils.DataUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName CardController
 * @Description TODO
 * @Author moses
 * @Date 2020/12/3
 **/
@RestController
@RequestMapping(value = "api")
public class CardController {
    @GetMapping("cards")
    public ResponseResult getCards(){
        List<Card> cards= DataUtil.initCards();
        return ResponseResult.success(cards);
    }
}
