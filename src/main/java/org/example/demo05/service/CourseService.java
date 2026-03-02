package org.example.demo05.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.demo05.entity.Course;

import java.util.List;

public interface CourseService extends IService<Course> {
    //分页查询
    List<Course> findAll(Page<Course> page);

    //查询全部
    List<Course> findAll();

    //唯一查询
    Course findById(Integer id);

    //保存
    boolean save(Course course);

    //修改
    boolean update(Course course);

    //批量删除
    int deleteByIds(List<Integer> ids);
}
