package org.example.demo05.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class Member {
    private int id;
    private int memberId;
    private String name;
    private String gender;
    private String phone;
    private LocalDate registerDate;
    private LocalDate expiredDate;
    private double balance;
    private boolean active;
}
