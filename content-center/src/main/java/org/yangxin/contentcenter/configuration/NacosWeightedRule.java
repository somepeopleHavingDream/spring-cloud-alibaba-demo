package org.yangxin.contentcenter.configuration;

import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.alibaba.nacos.NacosDiscoveryProperties;
import org.springframework.cloud.alibaba.nacos.ribbon.NacosServer;

import javax.annotation.Resource;

@Slf4j
public class NacosWeightedRule extends AbstractLoadBalancerRule {
    @Resource
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
        // 读取配置文件，并初始化 NacosWeightedRule
    }

    @Override
    public Server choose(Object key) {
        try {
            BaseLoadBalancer loadBalancer = (BaseLoadBalancer) this.getLoadBalancer();
            // 想要请求的微服务的名称
            String name = loadBalancer.getName();
            // 拿服务发现的相关 api
            NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();
            // nacos client 自动通过基于权重的负载均衡算法，给我们选择一个实例
            Instance instance = namingService.selectOneHealthyInstance(name);
            log.info("choose {} {}", instance.getPort(), instance);

            return new NacosServer(instance);
        } catch (Exception e) {
            log.error("choose error", e);
            return null;
        }
    }
}
