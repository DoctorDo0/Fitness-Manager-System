package org.example.demo05.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.demo05.entity.Course;
import org.example.demo05.entity.bean.CourseBean;
import org.example.demo05.utils.AuditEntity;

import java.util.List;

public interface CourseService extends IService<Course> {
    //分页查询
    List<Course> findAll(Page<Course> page);

    //查询全部
    List<Course> findAll();

    IPage<Course> getCourses(Page<?> page, CourseBean courseBean);

    //唯一查询
    Course findById(Integer id);

    //保存
    boolean save(Course course);

    //修改
    boolean update(Course course);

    //批量删除
    int deleteByIds(Integer[] ids, AuditEntity auditEntity);

    //批量恢复
    int restoreByIds(Integer[] ids, AuditEntity auditEntity);

    //主要信息，适配前端下拉列表(选择器)
    List<Course> getMainInfo();

    //课程数，适配前端EChart图表
    int getCourseCount();
}
