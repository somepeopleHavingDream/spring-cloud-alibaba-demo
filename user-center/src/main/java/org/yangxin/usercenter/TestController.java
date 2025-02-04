package org.yangxin.usercenter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yangxin.usercenter.dao.user.UserMapper;
import org.yangxin.usercenter.domain.entity.user.User;

import java.util.Date;

@RestController
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequiredArgsConstructor
public class TestController {

    private final UserMapper userMapper;

    @GetMapping("/test")
    public User testInsert() {
        User user = new User();
        user.setAvatarUrl("xxx");
        user.setBonus(100);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());

        this.userMapper.insertSelective(user);

        return user;
    }
}
