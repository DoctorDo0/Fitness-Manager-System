package org.example.demo05.service.implement;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.demo05.dao.AppointmentDAO;
import org.example.demo05.entity.*;
import org.example.demo05.service.AppointmentService;
import org.example.demo05.utils.AttendanceStatus;
import org.example.demo05.utils.CourseTime;
import org.example.demo05.utils.JsonResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class AppointmentServiceImplement implements AppointmentService {
    AppointmentDAO appointmentDAO;
    StudentServiceImplement studentServiceImplement;
    CourseInfoServiceImplement courseInfoServiceImplement;

    @Autowired
    public void setAppointmentDAO(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    @Autowired
    public void setStudentServiceImplement(StudentServiceImplement studentServiceImplement) {
        this.studentServiceImplement = studentServiceImplement;
    }

    @Autowired
    public void setCourseInfoServiceImplement(CourseInfoServiceImplement courseInfoServiceImplement) {
        this.courseInfoServiceImplement = courseInfoServiceImplement;
    }

    @Override
    public JsonResp getAppointment(Page<?> page, Map<String, String> params) {
        Appointment appointment = new Appointment();
        Student student = new Student();
        CourseInfo courseInfo = new CourseInfo();
        Course course = new Course();
        Teacher teacher = new Teacher();
        appointment.setStudent(student);
        appointment.setCourseInfo(courseInfo);
        courseInfo.setCourse(course);
        courseInfo.setTeacher(teacher);
        // 处理嵌套字段
        // 1. 点号格式: course.name
        // 2. 方括号格式: course[name]
        params.forEach((key, value) -> {
            if (key.contains("student")) {
                if (key.endsWith(".studentId") || key.endsWith("[studentId]")) {
                    if (value != null && !value.isEmpty()) {
                        appointment.getStudent().setStudentId(value);
                    }
                } else if (key.endsWith(".name") || key.endsWith("[name]")) {
                    if (value != null && !value.isEmpty()) {
                        appointment.getStudent().setName(value);
                    }
                }
            }
            if (key.contains("courseInfo")) {
                if (key.endsWith(".courseDate") || key.endsWith("[courseDate]")) {
                    if (value != null && !value.isEmpty()) {
                        appointment.getCourseInfo().setCourseDate(LocalDate.parse(value));
                    }
                } else if (key.endsWith(".coursePeriod") || key.endsWith("[coursePeriod]")) {
                    if (value != null && !value.isEmpty()) {
                        appointment.getCourseInfo().setCoursePeriod(Integer.valueOf(value));
                    }
                } else if (key.endsWith(".courseAddress") || key.endsWith("[courseAddress]")) {
                    if (value != null && !value.isEmpty()) {
                        appointment.getCourseInfo().setCourseAddress(value);
                    }
                }

                if (key.contains(".course") || key.contains("[course]")) {
                    if (key.endsWith(".courseId") || key.endsWith("[courseId]")) {
                        if (value != null && !value.isEmpty()) {
                            appointment.getCourseInfo().getCourse().setCourseId(value);
                        }
                    } else if (key.endsWith(".courseName") || key.endsWith("[courseName]")) {
                        if (value != null && !value.isEmpty()) {
                            appointment.getCourseInfo().getCourse().setCourseName(value);
                        }
                    }
                } else if (key.contains("teacher") || key.contains("[teacher]")) {
                    if (key.endsWith(".teacherId") || key.endsWith("[teacherId]")) {
                        if (value != null && !value.isEmpty()) {
                            appointment.getCourseInfo().getTeacher().setTeacherId(value);
                        }
                    } else if (key.endsWith(".name") || key.endsWith("[name]")) {
                        if (value != null && !value.isEmpty()) {
                            appointment.getCourseInfo().getTeacher().setName(value);
                        }
                    }
                }
            }
        });
        try (Page<?> _ = PageHelper.startPage(page.getPageNum(), page.getPageSize())) {
            List<Appointment> appointments = appointmentDAO.selectAll(appointment);
            for (Appointment a : appointments) {
                if (a.getRecord() != null) {
                    a.setRecordInfo(AttendanceStatus.getDescFromCode(a.getRecord()));
                }
            }
            PageInfo<?> pageInfo = new PageInfo<>(appointments);
            return JsonResp.success(pageInfo);
        }
    }

    // 强制新增
    @Override
    public JsonResp addAppointment(Appointment appointment) {
        if (!checkout(appointment)) {
            return JsonResp.error("参数错误或不存在");
        } else {
            return JsonResp.success(appointmentDAO.insert(appointment));
        }
    }

    @Override
    public JsonResp updateAppointment(Appointment appointment) {
        if (!checkout(appointment)) {
            return JsonResp.error("参数错误或不存在");
        } else {
            return JsonResp.success(appointmentDAO.update(appointment));
        }
    }

    // 强制删除
    @Override
    public JsonResp deleteAppointment(Integer[] ids) {
        return JsonResp.success(appointmentDAO.delete(ids));
    }

    // 预约(新增)
    @Override
    public JsonResp bookAppointment(Appointment appointment) {
        if (!checkout(appointment)) {
            return JsonResp.error("参数错误或不存在");
        } else if (checkTimeBefore(appointment.getCourseInfoId())) {
            return JsonResp.error("已过开课时间，无法预约");
        } else {
            return JsonResp.success(appointmentDAO.book(appointment));
        }
    }

    // 取消预约(删除)
    @Override
    public JsonResp cancelAppointment(Integer[] ids) {
        for (Integer id : ids) {
            if (checkTimeBefore(getCourseInfoIdByAppointmentId(id))) {
                return JsonResp.error("已过开课时间，无法取消预约");
            }
        }
        return JsonResp.success(appointmentDAO.cancel(ids));
    }

    // 设置签到状态-签到
    @Override
    public JsonResp attendAppointment(Integer[] ids) {
        for (Integer id : ids) {
            if (!checkTimeBefore(getCourseInfoIdByAppointmentId(id))) {
                return JsonResp.error("有课程未到开课时间，无法签到");
            }
        }
        return JsonResp.success(appointmentDAO.attendStat(ids, AttendanceStatus.ATTEND.getCode()));
    }

    // 设置签到状态-旷课
    @Override
    public JsonResp absentAppointment(Integer[] ids) {
        for (Integer id : ids) {
            if (checkTimeAfter(getCourseInfoIdByAppointmentId(id))) {
                return JsonResp.error("课程未结束，无法设置旷课");
            }
        }
        return JsonResp.success(appointmentDAO.attendStat(ids, AttendanceStatus.ABSENT.getCode()));
    }

    // 设置签到状态-迟到
    @Override
    public JsonResp lateAppointment(Integer[] ids) {
        for (Integer id : ids) {
            if (!(checkTimeBefore(getCourseInfoIdByAppointmentId(id)) && checkTimeAfter(getCourseInfoIdByAppointmentId(id)))) {
                return JsonResp.error("不在课程规定时间段内，无法设置迟到");
            }
        }
        return JsonResp.success(appointmentDAO.attendStat(ids, AttendanceStatus.LATE.getCode()));
    }

    // 设置签到状态-请假
    @Override
    public JsonResp leaveAppointment(Integer[] ids) {
        return JsonResp.success(appointmentDAO.attendStat(ids, AttendanceStatus.LEAVE.getCode()));
    }

    // 校验数据是否存在
    public Boolean checkout(Appointment appointment) {
        return studentServiceImplement.getByPrimaryKey(appointment.getStudentId()) != null
                && courseInfoServiceImplement.getByPrimaryKey(appointment.getCourseInfoId()) != null;
    }

    // 根据appointmentId获取courseInfoId
    public int getCourseInfoIdByAppointmentId(Integer appointmentId) {
        // 根据id获取appointment对象
        Appointment appointment = appointmentDAO.selectById(appointmentId);
        // 返回appointment对象中的courseInfoId
        return appointment.getCourseInfoId();
    }

    // 校验时间是否未到
    public Boolean checkTimeBefore(Integer courseInfoId) {
        //TimeFrom < now -> true
        // 当前时间点
        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        // 根据courseInfoId获取courseInfo对象
        CourseInfo courseInfo = courseInfoServiceImplement.getByPrimaryKey(courseInfoId);
        // 通过courseInfo对象中的courseDate和coursePeriod合成新的时间点
        LocalDateTime targetDateTimeFrom = LocalDateTime.of(
                courseInfo.getCourseDate(),
                CourseTime.getFromCoursePeriod(courseInfo.getCoursePeriod())
        );
        // 返回判断值
        return targetDateTimeFrom.isBefore(currentLocalDateTime);
    }

    // 校验时间是否超过
    public Boolean checkTimeAfter(Integer id) {
        //TimeTo > now -> true
        // 当前时间点
        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        // 根据id获取appointment对象
        Appointment appointment = appointmentDAO.selectById(id);
        // 根据appointment对象中的courseInfoId获取courseInfo对象
        CourseInfo courseInfo = courseInfoServiceImplement.getByPrimaryKey(appointment.getCourseInfoId());
        // 通过courseInfo对象中的courseDate和coursePeriod合成新的时间点
        LocalDateTime targetDateTimeTo = LocalDateTime.of(
                courseInfo.getCourseDate(),
                CourseTime.getToCoursePeriod(courseInfo.getCoursePeriod())
        );
        // 返回判断值
        return targetDateTimeTo.isAfter(currentLocalDateTime);
    }
}
