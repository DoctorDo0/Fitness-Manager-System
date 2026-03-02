package org.example.demo05.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.demo05.entity.Teacher;
import org.example.demo05.entity.bean.TeacherBean;
import org.example.demo05.utils.AuditEntity;

import java.util.List;

@Mapper
public interface TeacherDAO {
    int insert(Teacher teacher);

    int deleteByPrimaryKey(@Param("ids") Integer[] ids, @Param("auditEntity") AuditEntity auditEntity);

    int restoreByPrimaryKey(@Param("ids") Integer[] ids, @Param("auditEntity") AuditEntity auditEntity);

    int updateByPrimaryKey(Teacher teacher);

    //    Teacher selectByPrimaryKey(Integer id);
    List<Teacher> selectAll(TeacherBean teacherBean);

    int getTeachersCount(TeacherBean teacherBean);
}
