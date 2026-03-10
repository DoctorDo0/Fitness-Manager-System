package org.example.demo05.service.implement;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.demo05.dao.StudentDAO;
import org.example.demo05.entity.Student;
import org.example.demo05.entity.bean.StudentBean;
import org.example.demo05.service.StudentService;
import org.example.demo05.utils.AuditEntity;
import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class StudentServiceImplement implements StudentService {
    //强加密器
    private static final PasswordEncryptor pe = new StrongPasswordEncryptor();
    private StudentDAO studentDAO;

    @Autowired
    public void setStudentMapper(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public List<Student> getStudents(Page<?> page, StudentBean studentBean) {
        try (Page<?> _ = PageHelper.startPage(page.getPageNum(), page.getPageSize())) {
            return studentDAO.selectAll(studentBean);
        }
    }

    @Override
    public Integer getStudentsCount(StudentBean studentBean) {
        return studentDAO.getStudentsCount(studentBean);
    }

    @Override
    public Integer addStudent(Student student) {
        return studentDAO.insert(student);
    }

    @Override
    public Integer updateStudent(Student student) {
        if (!StringUtils.hasText(student.getStudentPassword())) {
            student.setStudentPassword(null);
        } else {
            student.setStudentPassword(pe.encryptPassword(student.getStudentPassword()));
        }
        return studentDAO.updateByPrimaryKey(student);
    }

    @Override
    public Integer deleteStudent(Integer[] ids, AuditEntity auditEntity) {
        return studentDAO.deleteByPrimaryKey(ids, auditEntity);
    }

    @Override
    public Integer restoreStudent(Integer[] ids, AuditEntity auditEntity) {
        return studentDAO.restoreByPrimaryKey(ids, auditEntity);
    }

    @Override
    public int batchSave(List<Student> students) {
        int count = 0;
        for (Student student : students) {
            //此处需要使用代理，如果不使用代理，无法触发aop，不会自动添加审计字段

            //TODO:
            //暂时将初始密码设置为123456
            student.setStudentPassword(pe.encryptPassword("123456"));

            int success = self().addStudent(student);
            if (success != 0) {
                count = count + success;
            }
        }
        return count;
    }

    public Student getByPrimaryKey(Integer id) {
        return this.studentDAO.selectByPrimaryKey(id);
    }

    @Override
    public List<Student> getMainInfo() {
        return this.studentDAO.getMainInfo();
    }

    private StudentService self() {
        return (StudentService) AopContext.currentProxy();
    }

    @Override
    public int getStudentCount() {
        return this.studentDAO.getStudentCount();
    }
}
