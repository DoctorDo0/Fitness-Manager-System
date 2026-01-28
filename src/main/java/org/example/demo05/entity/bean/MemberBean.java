package org.example.demo05.entity.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.demo05.entity.Member;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class MemberBean extends Member {
    private LocalDate registerDateFrom;
    private LocalDate registerDateTo;
    private LocalDate expiredDateFrom;
    private LocalDate expiredDateTo;
    private double balanceFrom;
    private double balanceTo;
}
