package org.example.demo05.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.demo05.entity.Subject;
import org.example.demo05.service.SubjectService;
import org.example.demo05.utils.JsonResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/subjects", produces = MediaType.APPLICATION_JSON_VALUE)
public class SubjectApi {
    private SubjectService subjectService;

    @Autowired
    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //分页查询
    @GetMapping
    public JsonResp list(@RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "10") Integer limit, Integer id) {
        //创建一个分页对象
        Page<Subject> p = new Page<>(page, limit);

        if (id == null) {
            List<Subject> subjects = this.subjectService.findAll(p);
            return JsonResp.success(Map.of("pi", p, "data", subjects));
        } else {
            Subject subject = this.subjectService.findById(id);
            return JsonResp.success(Map.of("pi", p, "data", subject));
        }
    }

    @DeleteMapping
    public JsonResp deleteByIds(@RequestBody Integer[] ids) {
        int cnt = this.subjectService.deleteByIds(List.of(ids));
        return JsonResp.success(cnt);
    }

    @PostMapping
    public JsonResp save(@RequestBody Subject subject) {
        boolean success = this.subjectService.save(subject);
        return JsonResp.success(success);
    }

    @PutMapping
    public JsonResp update(@RequestBody Subject subject) {
        boolean success = this.subjectService.update(subject);
        return JsonResp.success(success);
    }

}
