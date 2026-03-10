package org.example.demo05.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.demo05.entity.Student;
import org.example.demo05.entity.bean.StudentBean;
import org.example.demo05.utils.AuditEntity;

import java.util.List;

@Mapper
public interface StudentDAO {
    int insert(Student student);

    int deleteByPrimaryKey(@Param("ids") Integer[] ids, @Param("auditEntity") AuditEntity auditEntity);

    int restoreByPrimaryKey(@Param("ids") Integer[] ids, @Param("auditEntity") AuditEntity auditEntity);

    int updateByPrimaryKey(Student student);

    //    Student selectByPrimaryKey(Integer id);
    List<Student> selectAll(StudentBean studentBean);

    int getStudentsCount(StudentBean studentBean);

    Student selectByPrimaryKey(Integer id);

    List<Student> getMainInfo();

    int getStudentCount();
}
