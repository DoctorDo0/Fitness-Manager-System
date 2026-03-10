package org.example.demo05.service;

import com.github.pagehelper.Page;
import org.example.demo05.entity.Student;
import org.example.demo05.entity.bean.StudentBean;
import org.example.demo05.utils.AuditEntity;

import java.util.List;

public interface StudentService {
    List<Student> getStudents(Page<?> page, StudentBean studentBean);

    Integer getStudentsCount(StudentBean studentBean);

    Integer addStudent(Student student);

    Integer updateStudent(Student student);

    Integer deleteStudent(Integer[] ids, AuditEntity auditEntity);

    Integer restoreStudent(Integer[] ids, AuditEntity auditEntity);

    int batchSave(List<Student> students);

    //主要信息，适配前端下拉列表(选择器)
    List<Student> getMainInfo();

    //学生数，适配前端EChart图表
    int getStudentCount();
}
