package org.scorpio.spring.boot.dubbo.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @ClassName: DubboConsumer
 * @Description: Dubbo消费者自定义注解
 * @author Administrator
 * @date 2018年1月3日
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DubboConsumer {
    // 版本
    public String version() default "";
    // 远程调用超时时间(毫秒)
    public int timeout() default 6000;
    // 注册中心
    String registry() default "";
    // 服务分组
    String group() default "";
    // 客户端类型
    String client() default "";
    // 点对点直连服务提供地址
    String url() default "";
    String protocol() default "";
    // 检查服务提供者是否存在
    boolean check() default false;
    // lazy create connection
    boolean lazy() default true;
    // 重试次数
    int retries() default 0;
    // 最大并发调用
    int actives() default 30;
    // 负载均衡
    String loadbalance() default "random";
    // 是否异步
    boolean async() default false;
    // 异步发送是否等待发送成功
    boolean sent() default false;
}
