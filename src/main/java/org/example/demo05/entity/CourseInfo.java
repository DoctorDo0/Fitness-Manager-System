package org.example.demo05.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@TableName("cms_course_teacher")
@Getter
@Setter
@ToString
public class CourseInfo {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @NotNull(message = "课程ID不可为空")
    private Integer courseId;

    @NotNull(message = "教师ID不可为空")
    private Integer teacherId;

    @NotNull(message = "课程时间不可为空")
    @NotEmpty(message = "课程时间不可为空")
    @NotBlank(message = "课程时间不可为空")
    private LocalDate courseDate;

    @NotNull(message = "课程节数不可为空")
    @NotEmpty(message = "课程节数不可为空")
    @NotBlank(message = "课程节数不可为空")
    private Integer coursePeriod;

    @NotNull(message = "课程地址不可为空")
    @NotEmpty(message = "课程地址不可为空")
    @NotBlank(message = "课程节数不可为空")
    private String courseAddress;

    @NotNull(message = "课程可选最大人数不可为空")
    @NotEmpty(message = "课程可选最大人数不可为空")
    @NotBlank(message = "课程可选最大人数不可为空")
    private Integer maxNumber;

    private String currentNumberInfo;

    private Course course;
    private Teacher teacher;
}
