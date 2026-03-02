package org.example.demo05.entity;

import cn.idev.excel.annotation.ExcelIgnore;
import cn.idev.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.demo05.utils.validate.TeacherAdd;
import org.example.demo05.utils.validate.TeacherEdit;
import org.example.demo05.utils.AuditEntity;

import java.time.LocalDate;

@TableName("cms_teacher")
@Getter
@Setter
@ToString
public class Teacher extends AuditEntity /*implements Serializable*/ {
    @ExcelProperty("ID")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ExcelProperty("账号")
    @NotBlank(message = "账号ID不可为空", groups = {TeacherEdit.class, TeacherAdd.class})
    @Size(min = 6, max = 12, message = "账号ID长度必须介于6~12之间", groups = {TeacherEdit.class, TeacherAdd.class})
    private String teacherId;

    @ExcelIgnore
    @NotBlank(message = "密码不可为空", groups = TeacherAdd.class)
    @Size(min = 6, max = 12, message = "密码长度必须介于6~12之间", groups = {TeacherEdit.class, TeacherAdd.class})
    private String teacherPassword;

    @ExcelIgnore
    @TableField("portrait")
    private String avatar;

    @ExcelProperty("姓名")
    @NotBlank(message = "会员姓名不可为空", groups = {TeacherEdit.class, TeacherAdd.class})
    @Size(min = 2, max = 20, message = "姓名长度必须介于2~20之间", groups = {TeacherEdit.class, TeacherAdd.class})
    private String name;

    @ExcelProperty("性别")
    @NotBlank(message = "性别不可为空", groups = {TeacherEdit.class, TeacherAdd.class})
    @Pattern(regexp = "^男|女$", message = "性别必须是男或女", groups = {TeacherEdit.class, TeacherAdd.class})
    private String gender;

    @ExcelProperty("出生日期")
    private LocalDate birthday;

    @ExcelProperty("电话")
    @Pattern(regexp = "^\\d{11}$", message = "必须是11位手机号", groups = {TeacherEdit.class, TeacherAdd.class})
    private String phone;

    @ExcelProperty("邮箱")
    @Email(message = "邮箱格式不正确", groups = {TeacherEdit.class, TeacherAdd.class})
    private String email;

    @ExcelProperty("微信")
    private String wechat;

    @ExcelProperty("QQ")
    private String qq;

    @ExcelProperty("描述")
    private String description;

    @ExcelProperty("是否活跃")
    // 此处使用Boolean代替boolean，除getActive与isActive方法外，boolean会影响初始化赋值
    private Boolean active;
}
