package org.example.demo05.controller;

import com.github.pagehelper.Page;
import org.example.demo05.entity.CourseInfo;
import org.example.demo05.service.CourseInfoService;
import org.example.demo05.utils.JsonResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/course-info")
public class CourseInfoController {
    CourseInfoService courseInfoService;

    @Autowired
    public void setCourseInfoService(CourseInfoService courseInfoService) {
        this.courseInfoService = courseInfoService;
    }

    @GetMapping
    public JsonResp getCourseInfo(int page, int limit, @RequestParam Map<String, String> params) {
        try {
            Page<?> p = new Page<>(page, limit);
            return courseInfoService.getCourseInfo(p, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public JsonResp addCourseInfo(@RequestBody CourseInfo courseInfo) {
        try {
            return courseInfoService.addCourseInfo(courseInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping
    public JsonResp updateCourseInfo(@RequestBody CourseInfo courseInfo) {
        try {
            return courseInfoService.updateCourseInfo(courseInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping
    public JsonResp deleteCourseInfo(@RequestBody Integer[] ids) {
        if (ids != null && ids.length == 0) {
            return JsonResp.error(400, "id为空");
        }
        try {
            return courseInfoService.deleteCourseInfo(ids);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //主要信息，适配前端下拉列表(选择器)
    @GetMapping(path = "/mainInfo")
    public JsonResp getMainInfo() {
        return JsonResp.success(this.courseInfoService.getMainInfo());
    }

    //课程分组占比(教师数量)，适配前端EChart图表用
    @GetMapping(path = "/getCourseGroup")
    public JsonResp getCourseGroup() {
        return this.courseInfoService.getTeacherCountWithSameCourse();
    }
}
