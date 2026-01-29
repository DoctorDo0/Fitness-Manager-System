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
    private int memberId;
    private String name;
    private String gender;
    private String phone;
    private String email;
    private double balance;
    private boolean active;
}
