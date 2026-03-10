package org.example.demo05.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.demo05.entity.Course;
import org.example.demo05.entity.bean.CourseBean;
import org.example.demo05.utils.AuditEntity;

import java.util.List;

@Mapper
public interface CourseDAO extends BaseMapper<Course> {
    //pass
    int deleteByIds(@Param("ids") Integer[] ids, @Param("auditEntity") AuditEntity auditEntity);

    int restoreByIds(@Param("ids") Integer[] ids, @Param("auditEntity") AuditEntity auditEntity);

    IPage<Course> getCourses(Page<?> page, @Param("courseBean") CourseBean courseBean);

    List<Course> getMainInfo();

    int getCourseCount();
}
