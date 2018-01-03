package org.scorpio.spring.boot.dubbo.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;

/**
 * 
 * @ClassName: DubboProperties
 * @Description: 配置文件的读取
 * @author Administrator
 * @date 2018年1月3日
 */
@ConfigurationProperties(prefix = DubboProperties.DUBOO_PREFIX)
public class DubboProperties {
    public final static String DUBOO_PREFIX = "dubbo";

    private ApplicationConfig application;

    private RegistryConfig registry;

    private ProtocolConfig protocol;

    public ApplicationConfig getApplication() {
        return application;
    }

    public void setApplication(ApplicationConfig application) {
        this.application = application;
    }

    public RegistryConfig getRegistry() {
        return registry;
    }

    public void setRegistry(RegistryConfig registry) {
        this.registry = registry;
    }

    public ProtocolConfig getProtocol() {
        return protocol;
    }

    public void setProtocol(ProtocolConfig protocol) {
        this.protocol = protocol;
    }
}
