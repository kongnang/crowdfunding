package com.member.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author qiu
 * @create 2022-03-09 15:02
 */
@Component
@ConfigurationProperties(prefix = "short.message")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortMessageProperties {

    private String host;
    private String path;
    private String method;
    private String appcode;
    private String phone_number;
    private String template_id;

}
