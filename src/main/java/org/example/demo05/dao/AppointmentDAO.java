package org.example.demo05.dao;

import org.apache.ibatis.annotations.Mapper;
import org.example.demo05.entity.Appointment;

import java.util.List;

@Mapper
public interface AppointmentDAO {
    List<Appointment> selectAll(Appointment appointment);

    int insert(Appointment appointment);

    int update(Appointment appointment);

    int delete(Integer[] ids);

    Appointment selectById(Integer id);

    int book(Appointment appointment);

    int cancel(Integer[] ids);

    int attendStat(Integer[] ids, Integer status);
}
