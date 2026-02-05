package org.example.demo05.entity;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.demo05.utils.AuditEntity;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class Member extends AuditEntity {

    private int id;

    @NotEmpty(message = "账号ID不可为空")
    @Size(min = 6, max = 12, message = "账号ID长度必须介于6~12之间")
    private String memberId;

    @NotEmpty(message = "密码不可为空")
    @Size(min = 6, max = 12, message = "密码长度必须介于6~12之间")
    private String memberPassword;

    @NotEmpty(message = "会员姓名不可为空")
    @Size(min = 2, max = 20, message = "姓名长度必须介于2~20之间")
    private String name;

    @NotEmpty(message = "性别不可为空")
    @Pattern(regexp = "^男|女$", message = "性别必须是男或女")
    private String gender;

    private LocalDate birthday;

    @NotEmpty(message = "手机号不可为空")
    @Pattern(regexp = "^\\d{11}$", message = "必须是11位手机号")
    private String phone;

    @Email(message = "邮箱格式不正确")
    private String email;

    private String wechat;

    private String qq;

    private String description;

    private double balance;

    private boolean active;
}
