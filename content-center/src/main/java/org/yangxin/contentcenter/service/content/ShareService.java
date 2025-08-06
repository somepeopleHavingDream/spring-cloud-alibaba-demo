package org.yangxin.contentcenter.service.content;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.yangxin.contentcenter.dao.content.ShareMapper;
import org.yangxin.contentcenter.domain.dto.content.ShareDTO;
import org.yangxin.contentcenter.domain.dto.user.UserDTO;
import org.yangxin.contentcenter.domain.entity.content.Share;
import org.yangxin.contentcenter.feignclient.UserCenterFeignClient;

import javax.annotation.Resource;
import java.util.Objects;

@Service
@Slf4j
public class ShareService {
    @Resource
    private ShareMapper shareMapper;
    @Resource
    private UserCenterFeignClient userCenterFeignClient;

    public ShareDTO findById(Integer id) {
        Share share = this.shareMapper.selectByPrimaryKey(id);
        if (Objects.isNull(share)) {
            return null;
        }
        Integer userId = share.getUserId();

        UserDTO userDTO = userCenterFeignClient.findById(userId);
        if (Objects.isNull(userDTO)) {
            return null;
        }

        ShareDTO shareDTO = new ShareDTO();
        BeanUtils.copyProperties(share, shareDTO);
        shareDTO.setWxNickname(userDTO.getWxNickname());

        return shareDTO;
    }
}
