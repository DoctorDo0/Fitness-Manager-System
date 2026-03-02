package org.example.demo05.service;

import com.github.pagehelper.Page;
import org.example.demo05.entity.Teacher;
import org.example.demo05.entity.bean.TeacherBean;
import org.example.demo05.utils.AuditEntity;

import java.util.List;

public interface TeacherService {
    List<Teacher> getTeachers(Page<?> page, TeacherBean teacherBean);

    Integer getTeachersCount(TeacherBean teacherBean);

    Integer addTeacher(Teacher teacher);

    Integer updateTeacher(Teacher teacher);

    Integer deleteTeacher(Integer[] ids, AuditEntity auditEntity);

    Integer restoreTeacher(Integer[] ids, AuditEntity auditEntity);

    int batchSave(List<Teacher> teachers);
}
