package org.yangxin.contentcenter.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.yangxin.contentcenter.domain.dto.user.UserDTO;

@FeignClient(name = "user-center")
public interface UserCenterFeignClient {
    @GetMapping("/users/{id}")
    UserDTO findById(@PathVariable Integer id);
}
