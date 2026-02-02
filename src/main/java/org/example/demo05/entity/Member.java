package org.example.demo05.entity;

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
    private String memberId;
    private String memberPassword;
    private String name;
    private String gender;
    private LocalDate birthday;
    private String phone;
    private String email;
    private String wechat;
    private String qq;
    private String description;
    private double balance;
    private boolean active;
}
