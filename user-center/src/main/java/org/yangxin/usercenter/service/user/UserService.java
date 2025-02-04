package org.yangxin.usercenter.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yangxin.usercenter.dao.user.UserMapper;
import org.yangxin.usercenter.domain.entity.user.User;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public User findById(Integer id) {
        return this.userMapper.selectByPrimaryKey(id);
    }
}
