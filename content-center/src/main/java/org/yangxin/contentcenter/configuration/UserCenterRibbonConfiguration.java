package org.yangxin.contentcenter.configuration;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;
import ribbonconfiguration.RibbonConfiguration;

@RibbonClient(name = "user-center", configuration = RibbonConfiguration.class)
@Configuration
public class UserCenterRibbonConfiguration {
}
