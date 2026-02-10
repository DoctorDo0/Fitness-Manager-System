package org.example.demo05.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

//科目，课程类别
@TableName("t_subject")
@Getter
@Setter
public class Subject implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String description;
}
