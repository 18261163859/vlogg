package com.smx.vlogg.controller;

import org.springframework.core.SpringVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author moses
 * @Date 2020/12/3
 **/
@RestController
@RequestMapping(value="api")
public class HelloController {

    @GetMapping("Hello")
    public String hello(){
        return SpringVersion.getVersion();
    }
}
