package org.example.demo05.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.example.demo05.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    default User findByUsername(String username) {
        //封装的查询条件
        Wrapper<User> w = Wrappers.<User>query()
                .eq("username", username);
        return selectOne(w);
    }
}
