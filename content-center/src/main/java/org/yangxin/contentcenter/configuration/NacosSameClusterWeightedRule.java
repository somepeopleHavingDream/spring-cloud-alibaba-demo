package org.yangxin.contentcenter.configuration;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.core.Balancer;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.alibaba.nacos.NacosDiscoveryProperties;
import org.springframework.cloud.alibaba.nacos.ribbon.NacosServer;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class NacosSameClusterWeightedRule extends AbstractLoadBalancerRule {
    @Resource
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object key) {
        try {
            String clusterName = nacosDiscoveryProperties.getClusterName();
            BaseLoadBalancer loadBalancer = (BaseLoadBalancer) this.getLoadBalancer();
            // 想要请求的微服务的名称
            String name = loadBalancer.getName();
            // 拿服务发现的相关 api
            NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();
            // 找到指定服务的所有实例 a
            List<Instance> instanceList = namingService.selectInstances(name, true);
            // 过滤出相同集群下的所有实例 b
            List<Instance> sameClusterInstanceList = instanceList.stream()
                    .filter(instance -> Objects.equals(instance.getClusterName(), clusterName))
                    .collect(Collectors.toList());
            // 如果 b 是空，则用 a
            List<Instance> instancesToBeChose;
            if (CollectionUtils.isEmpty(sameClusterInstanceList)) {
                instancesToBeChose = instanceList;
                log.info("A cross-cluster call occurs {} {} {}", name, clusterName, instanceList);
            } else {
                instancesToBeChose = sameClusterInstanceList;
            }
            // 基于权重的负载均衡算法，返回 1 个实例
            Instance instance = ExtendBalancer.getHostByRandomWeight2(instancesToBeChose);
            if (Objects.isNull(instance)) {
                return null;
            }
            log.info("chose instance {} {}", instance.getPort(), instance);

            return new NacosServer(instance);
        } catch (NacosException e) {
            log.error("choose error", e);
            return null;
        }
    }
}

class ExtendBalancer extends Balancer {
    public static Instance getHostByRandomWeight2(List<Instance> hosts) {
        return getHostByRandomWeight(hosts);
    }
}
