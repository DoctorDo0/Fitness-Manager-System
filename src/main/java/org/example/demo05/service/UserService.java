package org.example.demo05.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.demo05.entity.User;

public interface UserService extends IService<User> {
    User findByUsername(String username);
}
