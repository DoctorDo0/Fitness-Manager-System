package org.example.demo05.controller;

import com.github.pagehelper.Page;
import org.example.demo05.entity.Appointment;
import org.example.demo05.entity.appointmentDTO.Credit;
import org.example.demo05.entity.appointmentDTO.RecordDesc;
import org.example.demo05.service.AppointmentService;
import org.example.demo05.utils.JsonResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/appointment", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppointmentController {
    AppointmentService appointmentService;

    @Autowired
    public void setAppointmentService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public JsonResp getAppointments(int page, int limit, @RequestParam Map<String, String> params) {
        try {
            Page<?> p = new Page<>(page, limit);
            return appointmentService.getAppointment(p, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public JsonResp addAppointment(@RequestBody Appointment appointment) {
        try {
            return appointmentService.addAppointment(appointment);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping
    public JsonResp updateAppointment(@RequestBody Appointment appointment) {
        try {
            return appointmentService.updateAppointment(appointment);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping
    public JsonResp deleteAppointment(@RequestBody Integer[] ids) {
        try {
            return appointmentService.deleteAppointment(ids);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(path = "/book")
    public JsonResp bookAppointment(@RequestBody Appointment appointment) {
        try {
            return appointmentService.bookAppointment(appointment);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(path = "/cancel")
    public JsonResp cancelAppointment(@RequestBody Integer[] ids) {
        if (ids != null && ids.length == 0) {
            return JsonResp.error(400, "id为空");
        }
        try {
            return appointmentService.cancelAppointment(ids);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(path = "/attend")
    public JsonResp attendAppointment(@RequestBody Integer[] ids) {
        if (ids != null && ids.length == 0) {
            return JsonResp.error(400, "id为空");
        }
        try {
            return appointmentService.attendAppointment(ids);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(path = "/absent")
    public JsonResp absentAppointment(@RequestBody Integer[] ids) {
        if (ids != null && ids.length == 0) {
            return JsonResp.error(400, "id为空");
        }
        try {
            return appointmentService.absentAppointment(ids);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(path = "/late")
    public JsonResp lateAppointment(@RequestBody Integer[] ids) {
        if (ids != null && ids.length == 0) {
            return JsonResp.error(400, "id为空");
        }
        try {
            return appointmentService.lateAppointment(ids);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(path = "/leave")
    public JsonResp leaveAppointment(@RequestBody Integer[] ids) {
        if (ids != null && ids.length == 0) {
            return JsonResp.error(400, "id为空");
        }
        try {
            return appointmentService.leaveAppointment(ids);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(path = "/recordDesc")
    public JsonResp recordDesc(@RequestBody RecordDesc recordDesc) {
        try {
            return appointmentService.addAppointmentRecordDesc(recordDesc.getIds(), recordDesc.getDesc());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(path = "/credit")
    public JsonResp setCredit(@RequestBody Credit credit) {
        if (credit.getIds() == null || credit.getIds().length == 0 || credit.getScore() == null) {
            return JsonResp.error(400, "id或score输入值为空");
        }
        try {
            return appointmentService.setScoreAndCredit(credit.getIds(), credit.getScore());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(path = "/getBookNumber")
    public JsonResp getBookNumber(@RequestParam Integer courseInfoId) {
        if (courseInfoId == null) {
            return JsonResp.error("课程信息ID不可为空");
        }
        return appointmentService.getBookNumber(courseInfoId);
    }

    //课程分组占比(学生数量)，适配前端EChart图表用
    @GetMapping(path = "/getCourseGroup")
    public JsonResp getCourseGroup() {
        return this.appointmentService.getStudentCountWithSameCourse();
    }
}
