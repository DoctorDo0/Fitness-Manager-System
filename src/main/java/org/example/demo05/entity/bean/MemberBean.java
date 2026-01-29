package org.example.demo05.entity.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.demo05.entity.Member;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
public class MemberBean extends Member {
    private String[] registerDateRange;
    private LocalDate registerDateFrom;
    private LocalDate registerDateTo;
    private String[] updateDateRange;
    private LocalDate updateDateFrom;
    private LocalDate updateDateTo;
    private double balanceFrom;
    private double balanceTo;

    public void setRegisterDateRange(String[] registerDateRange) {
        this.registerDateRange = registerDateRange;
        if (registerDateRange.length == 2) {
            String from = registerDateRange[0].trim();
            String to = registerDateRange[1].trim();
            registerDateFrom = LocalDate.parse(from, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            registerDateTo = LocalDate.parse(to, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }

    public void setUpdateDateRange(String[] updateDateRange) {
        this.updateDateRange = updateDateRange;
        if (updateDateRange.length == 2) {
            String from = updateDateRange[0].trim();
            String to = updateDateRange[1].trim();
            updateDateFrom = LocalDate.parse(from, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            updateDateTo = LocalDate.parse(to, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }
}
