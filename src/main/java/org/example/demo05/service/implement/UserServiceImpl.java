package org.example.demo05.service.implement;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.demo05.dao.UserMapper;
import org.example.demo05.entity.User;
import org.example.demo05.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User findByUsername(String username) {
        return this.getBaseMapper().findByUsername(username);
    }
}
