package org.example.demo05.service.implement;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.demo05.dao.CourseMapper;
import org.example.demo05.entity.Course;
import org.example.demo05.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@CacheConfig(cacheNames = "courses")
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    private CourseMapper courseMapper;

    @Autowired
    public void setCourseMapper(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    @Override
    public List<Course> findAll(Page<Course> page) {
        return this.courseMapper.selectList(page, null);
    }

    @Cacheable(key = "'all'")
    @Override
    public List<Course> findAll() {
        return this.courseMapper.selectList(null);
    }

    @Cacheable(key = "#id")
    @Override
    public Course findById(Integer id) {
        return this.courseMapper.selectById(id);
    }

    @CacheEvict(allEntries = true)
    @Override
    public boolean save(Course course) {
        return this.courseMapper.insert(course) > 0;
    }

    @CacheEvict(allEntries = true)
    @Override
    public boolean update(Course course) {
        return this.courseMapper.updateById(course) > 0;
    }

    @CacheEvict(allEntries = true)
    @Override
    public int deleteByIds(List<Integer> ids) {
        return this.courseMapper.deleteByIds(ids);
    }
}
