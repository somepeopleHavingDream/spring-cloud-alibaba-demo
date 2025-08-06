package org.yangxin.contentcenter.configuration;

import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;
import org.yangxin.ribbonconfiguration.RibbonConfiguration;

@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
@Configuration
public class UserCenterRibbonConfiguration {
}
