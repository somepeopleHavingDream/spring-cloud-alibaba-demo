package org.yangxin.ribbonconfiguration;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yangxin.contentcenter.configuration.NacosSameClusterWeightedRule;

@Configuration
public class RibbonConfiguration {
    @Bean
    public IRule ribbonRule() {
        return new NacosSameClusterWeightedRule();
    }
}
