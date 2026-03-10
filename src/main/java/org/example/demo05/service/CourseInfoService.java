package org.example.demo05.service;

import com.github.pagehelper.Page;
import org.example.demo05.entity.CourseInfo;
import org.example.demo05.utils.JsonResp;

import java.util.List;
import java.util.Map;

public interface CourseInfoService {
    JsonResp getCourseInfo(Page<?> page, Map<String, String> params);

    JsonResp addCourseInfo(CourseInfo courseInfo);

    JsonResp updateCourseInfo(CourseInfo courseInfo);

    JsonResp deleteCourseInfo(Integer[] ids);

    //主要信息，适配前端下拉列表(选择器)
    List<CourseInfo> getMainInfo();

    JsonResp getTeacherCountWithSameCourse();
}
