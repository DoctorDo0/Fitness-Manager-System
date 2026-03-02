package org.example.demo05.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.demo05.entity.Course;
import org.example.demo05.service.CourseService;
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

    //分页查询
    @GetMapping
    public JsonResp list(@RequestParam(defaultValue = "1") Integer page,
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
        int cnt = this.courseService.deleteByIds(List.of(ids));
        return JsonResp.success(cnt);
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

}
