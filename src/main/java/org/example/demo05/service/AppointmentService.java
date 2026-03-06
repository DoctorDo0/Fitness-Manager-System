package org.example.demo05.service;

import com.github.pagehelper.Page;
import org.example.demo05.entity.Appointment;
import org.example.demo05.utils.JsonResp;

import java.util.Map;

public interface AppointmentService {
    JsonResp getAppointment(Page<?> page, Map<String, String> params);

    JsonResp addAppointment(Appointment appointment);

    JsonResp updateAppointment(Appointment appointment);

    JsonResp deleteAppointment(Integer[] ids);

    JsonResp bookAppointment(Appointment appointment);

    JsonResp cancelAppointment(Integer[] ids);

    JsonResp attendAppointment(Integer[] ids);

    JsonResp absentAppointment(Integer[] ids);

    JsonResp lateAppointment(Integer[] ids);

    JsonResp leaveAppointment(Integer[] ids);
}
