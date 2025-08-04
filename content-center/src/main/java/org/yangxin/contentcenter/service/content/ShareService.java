package org.yangxin.contentcenter.service.content;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.yangxin.contentcenter.dao.content.ShareMapper;
import org.yangxin.contentcenter.domain.dto.content.ShareDTO;
import org.yangxin.contentcenter.domain.dto.user.UserDTO;
import org.yangxin.contentcenter.domain.entity.content.Share;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShareService {
    private final ShareMapper shareMapper;
    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;

    public ShareDTO findById(Integer id) {
        Share share = this.shareMapper.selectByPrimaryKey(id);
        if (Objects.isNull(share)) {
            return null;
        }
        Integer userId = share.getUserId();

        List<ServiceInstance> instanceList = discoveryClient.getInstances("user-center");
        String targetURL = instanceList.stream()
                .map(tmp -> tmp.getUri().toString() + "/users/{id}")
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("当前没有实例"));
        log.info("targetURL:{}", targetURL);

        UserDTO userDTO = this.restTemplate.getForObject(targetURL, UserDTO.class, userId);
        if (Objects.isNull(userDTO)) {
            return null;
        }

        ShareDTO shareDTO = new ShareDTO();
        BeanUtils.copyProperties(share, shareDTO);
        shareDTO.setWxNickname(userDTO.getWxNickname());

        return shareDTO;
    }
}
