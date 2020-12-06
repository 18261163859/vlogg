package com.smx.vlogg.utils;

import com.smx.vlogg.VloggApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = VloggApplication.class)
@Slf4j
class SmsUtilTest {

    @Resource
    private SmsUtil smsUtil;

    @Test
    void sendSms() {
        smsUtil.sendSms("15301562853","123456");
    }
}