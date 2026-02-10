package org.example.demo05.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.demo05.entity.Subject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SubjectMapper extends BaseMapper<Subject> {
    //pass
}
