package com.ppd.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@Data
public class PropsConfig {

    //@Value("${product.file.path}")
    private String prodFilePath;


}
