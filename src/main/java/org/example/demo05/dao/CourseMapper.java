package org.example.demo05.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.demo05.entity.Course;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    //pass
}
