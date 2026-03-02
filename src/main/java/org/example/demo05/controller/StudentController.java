package org.example.demo05.controller;

import cn.idev.excel.FastExcel;
import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.read.listener.ReadListener;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo05.entity.Student;
import org.example.demo05.entity.bean.StudentBean;
import org.example.demo05.service.StudentService;
import org.example.demo05.service.UploadService;
import org.example.demo05.service.implement.StudentServiceImplement;
import org.example.demo05.service.implement.UploadServiceImpl;
import org.example.demo05.utils.AuditEntity;
import org.example.demo05.utils.JsonResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping(value = "/api/student", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentController {
    StudentServiceImplement studentService;
    UploadService uploadService;

    @Autowired
    public void setStudentService(StudentServiceImplement studentService) {
        this.studentService = studentService;
    }

    @Autowired
    public void setUploadService(UploadServiceImpl uploadService) {
        this.uploadService = uploadService;
    }

    @GetMapping
    public JsonResp getStudents(int page, int limit, StudentBean studentBean) {
        try {
            Page<?> p = new Page<>(page, limit);
            List<Student> students = studentService.getStudents(p, studentBean);
            PageInfo<?> pageInfo = new PageInfo<>(students);
            return JsonResp.success(pageInfo);
        } catch (Exception e) {
//            return JsonResp.error(500, e.toString());
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public JsonResp addStudent(@RequestBody Student student) {
        try {
            int resp = studentService.addStudent(student);
            return JsonResp.success(resp);
        } catch (Exception e) {
//            return JsonResp.error(500, e.toString());
            throw new RuntimeException(e);
        }
    }

    @PutMapping
    public JsonResp updateStudent(@RequestBody Student student) {
        try {
            int resp = studentService.updateStudent(student);
            return JsonResp.success(resp);
        } catch (Exception e) {
//            return JsonResp.error(500, e.toString());
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping
    public JsonResp deleteStudent(@RequestBody Integer[] ids) {
        if (ids != null && ids.length == 0) {
            return JsonResp.error(400, "id为空");
        }
        try {
            int res = studentService.deleteStudent(ids, new AuditEntity());
            return JsonResp.success(res);
        } catch (Exception e) {
//            return JsonResp.error(500, e.toString());
            throw new RuntimeException(e);
        }
    }

    @PatchMapping
    public JsonResp restoreStudent(@RequestBody Integer[] ids) {
        if (ids != null && ids.length == 0) {
            return JsonResp.error(400, "id为空");
        }
        try {
            int res = studentService.restoreStudent(ids, new AuditEntity());
            return JsonResp.success(res);
        } catch (Exception e) {
//            return JsonResp.error(500, e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * 导出会员到excel
     */
    @GetMapping(value = "/export", produces = "application/vnd.ms-excel")
    public void exportToExcel(StudentBean studentBean, HttpServletResponse resp) throws IOException {
        // mybatisplus的组件page设置pageSize为-1时可获取所有项，pageHelper不包含此功能
//        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Student> p = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(1, -1);

        // 获取最大数量
        int count = this.studentService.getStudentsCount(studentBean);
        Page<Student> p = new Page<>(1, count);
//        //满足条件的会员
//        p = studentService.getStudents(p, studentBean);
//        //会员列表
//        List<Student> students = p.getRecords();
        List<Student> students = studentService.getStudents(p, studentBean);

        LocalDateTime now = LocalDateTime.now();
        //下载文件名
        String fileName = "会员信息表-" + now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        // 这里URLEncoder.encode可以防止中文乱码 当然这和easy-excel没有关系
        fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");

        //设置请求头，表示下载
        resp.setHeader("Content-Disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        //handler为要排除的属性，此属性比较特殊，并不是直接定义在Student这个类中，而是定义在Student的动态代理子类之中（由MyBatis通过Javassist创建），
        //EasyExcel并不能从Student这个类中探测到这个字段，所以依旧会进行转换并导出，此处手动将此属性排除
        //对于原生使用Student类型，未创建动态代理类型的则无需考虑上述问题
        List<String> excludeProperties = List.of("handler");

        //链式操作，既可以通过白名单设置要包含的列（仅导出包含列），也可以通过黑名单设置要排除的列（除排除列全部导出）。
        //此处配置的白名单列和黑名单列，会覆盖模型类中的注解定义（优先级比注解定义高），如：@ExcelProperty和@ExcelIgnore。
        //registerConverter表示全局适用，而写在属性的注解中，表示仅所属注解适用
        FastExcel.write(resp.getOutputStream(), Student.class)
                .excludeColumnFieldNames(excludeProperties).sheet("会员信息表")
                .doWrite(students);

    }

    /**
     * 从excel导入模型数据
     */
    @PostMapping("/import")
    public ResponseEntity<JsonResp> importFromExcel(MultipartFile file) throws Exception {
        InputStream is = file.getInputStream();

        //定义一个上传监听器
        UploadListener listener = new UploadListener(studentService);

        FastExcel.read(is, Student.class, listener).sheet().doRead();

        return ResponseEntity.ok(JsonResp.success(listener.count()));
    }

    //excel上传监听器
    private static class UploadListener implements ReadListener<Student> {
        private final List<Student> students = new ArrayList<>();
        private final StudentService studentService;

        public UploadListener(StudentService studentService) {
            this.studentService = studentService;
        }

        //每读取完excel的一行后触发
        @Override
        public void invoke(Student student, AnalysisContext analysisContext) {
            students.add(student);
        }

        //全部解析结束后触发
        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            for (Student student : students) {
                student.setId(null);//保存时不应有id
            }

            int _ = this.studentService.batchSave(students);
        }

        public int count() {
            return students.size();
        }
    }

    //头像上传
    @PostMapping("/avatar")
    public JsonResp uploadAvatar(MultipartFile file) {
        String url = this.uploadService.upload(file, "student_avatar");
        return JsonResp.success(url);
    }
}
