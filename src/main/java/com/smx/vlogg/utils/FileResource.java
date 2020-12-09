package com.smx.vlogg.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @ClassName FileResource
 * @Description TODO
 * @Author moses
 * @Date 2020/12/7
 **/
@Data
@Component
@PropertySource("classpath:file.properties")
@ConfigurationProperties(prefix = "file")
public class FileResource {
    private String host;
    private String endpoint;
    private String bucketName;
    private String objectName;
    private String ossHost;
}
