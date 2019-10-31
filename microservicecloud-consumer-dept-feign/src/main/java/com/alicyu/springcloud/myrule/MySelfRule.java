package com.alicyu.springcloud.myrule;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign整合ribbon后实现自定义的负载均衡方式
 * @author zph
 * @classname MySelfRule
 * @description TODO
 * @date 2019/9/4 22:58
 */
@Configuration
public class MySelfRule {
    @Bean
    public IRule myRule(){
        //return new RandomRule();//Ribbon默认是轮询，我自定义为随机

        return new RandomRule_ZY();//我自定义为每个机器被访问5次

    }
}
