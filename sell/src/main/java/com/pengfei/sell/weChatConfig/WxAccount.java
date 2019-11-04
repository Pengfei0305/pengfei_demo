package com.pengfei.sell.weChatConfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Pengfei
 * @date 2019-11-01 17:11
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WxAccount {
    private String appId;

    private String appSecret;
}
