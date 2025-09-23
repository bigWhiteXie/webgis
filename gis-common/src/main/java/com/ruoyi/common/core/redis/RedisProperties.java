package com.ruoyi.common.core.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Redis配置属性类
 * 
 * @author ruoyi
 */
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {
    
    /** 是否启用redis */
    private boolean enabled = true;
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}