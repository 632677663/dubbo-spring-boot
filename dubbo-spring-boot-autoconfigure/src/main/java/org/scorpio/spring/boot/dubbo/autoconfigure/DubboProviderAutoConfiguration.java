package org.scorpio.spring.boot.dubbo.autoconfigure;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.scorpio.spring.boot.dubbo.annotation.DubboProvider;
import org.scorpio.spring.boot.dubbo.bean.ServiceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.annotation.Service;

/**
 * 
* @ClassName: DubboProviderAutoConfiguration  
* @Description: TODO(这里用一句话描述这个类的作用)  
* @author Administrator  
* @date 2018年1月3日
 */
@Configuration
@AutoConfigureAfter(DubboAutoConfiguration.class)
@ConditionalOnClass(Service.class)
@EnableConfigurationProperties(DubboProperties.class)
public class DubboProviderAutoConfiguration {
    
    
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ApplicationConfig applicationConfig;
    @Autowired
    private RegistryConfig registryConfig;
    @Autowired
    private ProtocolConfig protocol;
    
    @PostConstruct
    public void init() throws Exception {
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(DubboProvider.class);
        for (Map.Entry<String, Object> entry : beanMap.entrySet())
            registerDubboProvider(entry.getKey(), entry.getValue());
    }
    
    private void registerDubboProvider(String beanName, Object bean) throws Exception {
        DubboProvider service = applicationContext.findAnnotationOnBean(beanName, DubboProvider.class);
        ServiceBean<Object> serviceConfig = new ServiceBean<Object>(service);
        if (void.class.equals(service.interfaceClass()) && "".equals(service.interfaceName())) {
            if (bean.getClass().getInterfaces().length > 0)
                serviceConfig.setInterface(bean.getClass().getInterfaces()[0]);
            else
                throw new IllegalStateException("Failed to export remote service class " + bean.getClass().getName()
                        + ", cause: The @Service undefined interfaceClass or interfaceName, and the service class unimplemented any interfaces.");
        }
        /*String version = service.version();
        version = StringUtils.isNotBlank(version) ? version : configuration.getVersion();
        if (StringUtils.isNoneBlank(version))
            serviceConfig.setVersion(version);
        String group = service.group();
        group = StringUtils.isNotBlank(group) ? group : configuration.getGroup();
        if (StringUtils.isNotBlank(group))
            serviceConfig.setGroup(group);*/
        serviceConfig.setApplicationContext(applicationContext);
        serviceConfig.setApplication(applicationConfig);
        serviceConfig.setProtocol(protocol);
        serviceConfig.setRegistry(registryConfig);
        serviceConfig.afterPropertiesSet();
        serviceConfig.setRef(bean);
        serviceConfig.export();
    }

}
