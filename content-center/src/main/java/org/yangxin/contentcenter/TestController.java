package org.yangxin.contentcenter;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yangxin.contentcenter.dao.content.ShareMapper;
import org.yangxin.contentcenter.domain.entity.content.Share;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final ShareMapper shareMapper;
    private final DiscoveryClient discoveryClient;

    @GetMapping("/test")
    public List<Share> testInsert() {
        Share share = new Share();
        share.setCreateTime(new Date());
        share.setUpdateTime(new Date());
        share.setTitle("xxx");
        share.setCover("xxx");
        share.setAuthor("yangxin");
        share.setBuyCount(1);

        this.shareMapper.insertSelective(share);

        return this.shareMapper.selectAll();
    }

    @GetMapping("/test2")
    public List<ServiceInstance> getInstances() {
        return this.discoveryClient.getInstances("user-center");
    }
}
