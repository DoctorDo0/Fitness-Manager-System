package org.example.demo05.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.demo05.entity.Subject;

import java.util.List;

public interface SubjectService {
    //分页查询
    List<Subject> findAll(Page<Subject> page);

    //查询全部
    List<Subject> findAll();

    //唯一查询
    Subject findById(Integer id);

    //保存
    boolean save(Subject subject);

    //修改
    boolean update(Subject subject);

    //批量删除
    int deleteByIds(List<Integer> ids);
}
