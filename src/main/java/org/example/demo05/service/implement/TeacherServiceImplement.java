package org.example.demo05.service.implement;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.demo05.dao.TeacherDAO;
import org.example.demo05.entity.Teacher;
import org.example.demo05.entity.bean.TeacherBean;
import org.example.demo05.service.TeacherService;
import org.example.demo05.utils.AuditEntity;
import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TeacherServiceImplement implements TeacherService {
    //强加密器
    private static final PasswordEncryptor pe = new StrongPasswordEncryptor();
    private TeacherDAO teacherDAO;

    @Autowired
    public void setTeacherMapper(TeacherDAO teacherDAO) {
        this.teacherDAO = teacherDAO;
    }

    @Override
    public List<Teacher> getTeachers(Page<?> page, TeacherBean teacherBean) {
        try (Page<?> _ = PageHelper.startPage(page.getPageNum(), page.getPageSize())) {
            return teacherDAO.selectAll(teacherBean);
        }
    }

    @Override
    public Integer getTeachersCount(TeacherBean teacherBean) {
        return teacherDAO.getTeachersCount(teacherBean);
    }

    @Override
    public Integer addTeacher(Teacher teacher) {
        return teacherDAO.insert(teacher);
    }

    @Override
    public Integer updateTeacher(Teacher teacher) {
        if (!StringUtils.hasText(teacher.getTeacherPassword())) {
            teacher.setTeacherPassword(null);
        } else {
            teacher.setTeacherPassword(pe.encryptPassword(teacher.getTeacherPassword()));
        }
        return teacherDAO.updateByPrimaryKey(teacher);
    }

    @Override
    public Integer deleteTeacher(Integer[] ids, AuditEntity auditEntity) {
        return teacherDAO.deleteByPrimaryKey(ids, auditEntity);
    }

    @Override
    public Integer restoreTeacher(Integer[] ids, AuditEntity auditEntity) {
        return teacherDAO.restoreByPrimaryKey(ids, auditEntity);
    }

    @Override
    public int batchSave(List<Teacher> teachers) {
        int count = 0;
        for (Teacher teacher : teachers) {
            //此处需要使用代理，如果不使用代理，无法触发aop，不会自动添加审计字段

            //TODO:
            //暂时将初始密码设置为123456
            teacher.setTeacherPassword(pe.encryptPassword("123456"));

            int success = self().addTeacher(teacher);
            if (success != 0) {
                count = count + success;
            }
        }
        return count;
    }

    private TeacherService self() {
        return (TeacherService) AopContext.currentProxy();
    }
}
