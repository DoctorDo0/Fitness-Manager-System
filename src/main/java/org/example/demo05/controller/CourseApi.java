package org.example.demo05.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.demo05.entity.Course;
import org.example.demo05.entity.bean.CourseBean;
import org.example.demo05.service.CourseService;
import org.example.demo05.utils.AuditEntity;
import org.example.demo05.utils.JsonResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/course", produces = MediaType.APPLICATION_JSON_VALUE)
public class CourseApi {
    private CourseService courseService;

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public JsonResp getCourses(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "10") Integer limit, CourseBean courseBean) {
        try {
            Page<?> p = new Page<>(page, limit);
            IPage<Course> courses = this.courseService.getCourses(p, courseBean);
            return JsonResp.success(courses);
        } catch (Exception e) {
//            return JsonResp.error(500, e.toString());
            throw new RuntimeException(e);
        }
    }

    //分页查询
    @GetMapping("/getById")
    public JsonResp getCourse(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer limit, Integer id) {
        //创建一个分页对象
        Page<Course> p = new Page<>(page, limit);

        if (id == null) {
            List<Course> courses = this.courseService.findAll(p);
            return JsonResp.success(Map.of("pi", p, "data", courses));
        } else {
            Course course = this.courseService.findById(id);
            return JsonResp.success(Map.of("pi", p, "data", course));
        }
    }

    @DeleteMapping
    public JsonResp deleteByIds(@RequestBody Integer[] ids) {
        if (ids != null && ids.length == 0) {
            return JsonResp.error(400, "id为空");
        }
        try {
            int cnt = this.courseService.deleteByIds(ids, new AuditEntity());
            return JsonResp.success(cnt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping
    public JsonResp restoreByIds(@RequestBody Integer[] ids) {
        if (ids != null && ids.length == 0) {
            return JsonResp.error(400, "id为空");
        }
        try {
            int cnt = courseService.restoreByIds(ids, new AuditEntity());
            return JsonResp.success(cnt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public JsonResp save(@RequestBody Course course) {
        boolean success = this.courseService.save(course);
        return JsonResp.success(success);
    }

    @PutMapping
    public JsonResp update(@RequestBody Course course) {
        boolean success = this.courseService.update(course);
        return JsonResp.success(success);
    }

    //主要信息，适配前端下拉列表(选择器)
    @GetMapping(path = "/mainInfo")
    public JsonResp getMainInfo() {
        return JsonResp.success(this.courseService.getMainInfo());
    }

    //课程数，适配前端EChart图表用
    @GetMapping(path = "/getCount")
    public JsonResp getCount() {
        return JsonResp.success(this.courseService.getCourseCount());
    }
}
