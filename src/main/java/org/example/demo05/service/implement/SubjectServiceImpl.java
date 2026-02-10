package org.example.demo05.service.implement;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.demo05.dao.SubjectMapper;
import org.example.demo05.entity.Subject;
import org.example.demo05.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@CacheConfig(cacheNames = "subjects")
@Service
public class SubjectServiceImpl implements SubjectService {
    private SubjectMapper subjectMapper;

    @Autowired
    public void setSubjectMapper(SubjectMapper subjectMapper) {
        this.subjectMapper = subjectMapper;
    }

    @Override
    public List<Subject> findAll(Page<Subject> page) {
        return this.subjectMapper.selectList(page, null);
    }

    @Cacheable(key = "'all'")
    @Override
    public List<Subject> findAll() {
        return this.subjectMapper.selectList(null);
    }

    @Cacheable(key = "#id")
    @Override
    public Subject findById(Integer id) {
        return this.subjectMapper.selectById(id);
    }

    @CacheEvict(allEntries = true)
    @Override
    public boolean save(Subject subject) {
        return this.subjectMapper.insert(subject) > 0;
    }

    @CacheEvict(allEntries = true)
    @Override
    public boolean update(Subject subject) {
        return this.subjectMapper.updateById(subject) > 0;
    }

    @CacheEvict(allEntries = true)
    @Override
    public int deleteByIds(List<Integer> ids) {
        return this.subjectMapper.deleteByIds(ids);
    }
}
