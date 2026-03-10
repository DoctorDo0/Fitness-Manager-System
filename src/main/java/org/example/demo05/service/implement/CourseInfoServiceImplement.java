package org.example.demo05.service.implement;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.demo05.dao.CourseInfoDAO;
import org.example.demo05.entity.Course;
import org.example.demo05.entity.CourseInfo;
import org.example.demo05.entity.Teacher;
import org.example.demo05.service.CourseInfoService;
import org.example.demo05.utils.JsonResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class CourseInfoServiceImplement implements CourseInfoService {
    CourseInfoDAO courseInfoDAO;
    CourseServiceImpl courseServiceImpl;
    TeacherServiceImplement teacherServiceImplement;

    @Autowired
    public void setCourseInfoMapper(CourseInfoDAO courseInfoDAO) {
        this.courseInfoDAO = courseInfoDAO;
    }

    @Autowired
    public void setCourseServiceImpl(CourseServiceImpl courseServiceImpl) {
        this.courseServiceImpl = courseServiceImpl;
    }

    @Autowired
    public void setTeacherServiceImplement(TeacherServiceImplement teacherServiceImplement) {
        this.teacherServiceImplement = teacherServiceImplement;
    }

    @Override
    public JsonResp getCourseInfo(Page<?> page, Map<String, String> params) {
        CourseInfo courseInfo = new CourseInfo();
        Course course = new Course();
        Teacher teacher = new Teacher();
        courseInfo.setCourse(course);
        courseInfo.setTeacher(teacher);
        // 基础字段
        if (params.containsKey("courseDate")) {
            if (params.get("courseDate") != null && !params.get("courseDate").isEmpty()) {
                courseInfo.setCourseDate(LocalDate.parse(params.get("courseDate")));
            }
        }
        if (params.containsKey("coursePeriod")) {
            if (params.get("coursePeriod") != null && !params.get("coursePeriod").isEmpty()) {
                courseInfo.setCoursePeriod(Integer.parseInt(params.get("coursePeriod")));
            }
        }
        if (params.containsKey("courseAddress")) {
            if (params.get("courseAddress") != null && !params.get("courseAddress").isEmpty()) {
                courseInfo.setCourseAddress(params.get("courseAddress"));
            }
        }

        // 处理嵌套字段 - 支持多种格式
        // 1. 点号格式: course.name
        // 2. 方括号格式: course[name]
        params.forEach((key, value) -> {
            if (key.contains("course")) {
                if (key.endsWith(".courseName") || key.endsWith("[courseName]")) {
                    if (value != null && !value.isEmpty()) {
                        courseInfo.getCourse().setCourseName(value);
                    }
                } else if (key.endsWith(".courseId") || key.endsWith("[courseId]")) {
                    if (value != null && !value.isEmpty()) {
                        courseInfo.getCourse().setCourseId(value);
                    }
                }
            }
            if (key.contains("teacher")) {
                if (key.endsWith(".name") || key.endsWith("[name]")) {
                    if (value != null && !value.isEmpty()) {
                        courseInfo.getTeacher().setName(value);
                    }
                } else if (key.endsWith(".teacherId") || key.endsWith("[teacherId]")) {
                    if (value != null && !value.isEmpty()) {
                        courseInfo.getTeacher().setTeacherId(value);
                    }
                }
            }
        });
        try (Page<?> _ = PageHelper.startPage(page.getPageNum(), page.getPageSize())) {
            PageInfo<?> pageInfo = new PageInfo<>(courseInfoDAO.selectAll(courseInfo));
            return JsonResp.success(pageInfo);
        }
    }

    @Override
    public JsonResp addCourseInfo(CourseInfo courseInfo) {
        if (!checkout(courseInfo)) {
            return JsonResp.error("参数错误或不存在");
        } else {
            return JsonResp.success(courseInfoDAO.insert(courseInfo));
        }
    }

    @Override
    public JsonResp updateCourseInfo(CourseInfo courseInfo) {
        if (!checkout(courseInfo)) {
            return JsonResp.error("参数错误或不存在");
        } else {
            return JsonResp.success(courseInfoDAO.updateByPrimaryKey(courseInfo));
        }
    }

    @Override
    public JsonResp deleteCourseInfo(Integer[] ids) {
        return JsonResp.success(courseInfoDAO.deleteByPrimaryKey(ids));
    }

    //校验数据是否存在
    public Boolean checkout(CourseInfo courseInfo) {
        return courseServiceImpl.findById(courseInfo.getCourseId()) != null
                && teacherServiceImplement.getByPrimaryKey(courseInfo.getTeacherId()) != null
                && courseInfo.getCoursePeriod() >= 1 && courseInfo.getCoursePeriod() <= 4;
    }

    public CourseInfo getByPrimaryKey(Integer id) {
        return this.courseInfoDAO.selectByPrimaryKey(id);
    }

    //主要数据，前端下拉列表用
    @Override
    public List<CourseInfo> getMainInfo() {
        return this.courseInfoDAO.getMainInfo();
    }

    @Override
    public JsonResp getTeacherCountWithSameCourse() {
        return JsonResp.success(courseInfoDAO.getTeacherCountWithSameCourse());
    }
}
