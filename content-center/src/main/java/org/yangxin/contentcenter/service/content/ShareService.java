package org.yangxin.contentcenter.service.content;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.yangxin.contentcenter.dao.content.ShareMapper;
import org.yangxin.contentcenter.domain.dto.content.ShareDTO;
import org.yangxin.contentcenter.domain.dto.user.UserDTO;
import org.yangxin.contentcenter.domain.entity.content.Share;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ShareService {

    private final ShareMapper shareMapper;
    private final RestTemplate restTemplate;

    public ShareDTO findById(Integer id) {
        Share share = this.shareMapper.selectByPrimaryKey(id);
        if (Objects.isNull(share)) {
            return null;
        }
        Integer userId = share.getUserId();

        UserDTO userDTO = this.restTemplate.getForObject("http://localhost:8080/users/{id}", UserDTO.class, userId);
        if (Objects.isNull(userDTO)) {
            return null;
        }

        ShareDTO shareDTO = new ShareDTO();
        BeanUtils.copyProperties(share, shareDTO);
        shareDTO.setWxNickname(userDTO.getWxNickname());

        return shareDTO;
    }
}
