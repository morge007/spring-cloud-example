package com.mghio.ucenter.manager;

import com.mghio.entity.User;
import com.mghio.mapper.UserMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UserManager {

    @Resource
    private UserMapper userMapper;

    public User getUserByUserId(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

}
