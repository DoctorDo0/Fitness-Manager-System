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
import org.example.demo05.utils.AuditEntity;

import java.io.Serializable;

//科目，课程类别
@TableName("cms_course")
@Getter
@Setter
@ToString
public class Course extends AuditEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @NotBlank(message = "课程代码不可为空")
    @NotEmpty(message = "课程代码不可为空")
    @NotNull(message = "课程代码不可为空")
    private String courseId;

    @NotBlank(message = "课程名称不可为空")
    @NotEmpty(message = "课程名称不可为空")
    @NotNull(message = "课程名称不可为空")
    private String courseName;

    private Double credits;

    private String description;

    private Boolean active;
}
