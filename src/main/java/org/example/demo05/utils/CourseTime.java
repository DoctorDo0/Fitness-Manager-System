package org.example.demo05.utils;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public enum CourseTime {
    Course1(1, LocalTime.of(8, 0), LocalTime.of(10, 0)),
    Course2(2, LocalTime.of(10, 0), LocalTime.of(12, 0)),
    Course3(3, LocalTime.of(14, 0), LocalTime.of(16, 0)),
    Course4(4, LocalTime.of(18, 0), LocalTime.of(20, 0));

    private final int coursePeriod;
    private final LocalTime timeFrom;
    private final LocalTime timeTo;

    CourseTime(int coursePeriod, LocalTime timeFrom, LocalTime timeTo) {
        this.coursePeriod = coursePeriod;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
    }

    // 根据code获取开始时间
    public static LocalTime getFromCoursePeriod(int coursePeriod) {
        for (CourseTime status : values()) {
            if (status.coursePeriod == coursePeriod) {
                return status.timeFrom;
            }
        }
        throw new IllegalArgumentException("无效的时间段: " + coursePeriod);
    }

    // 根据code获取结束时间
    public static LocalTime getToCoursePeriod(int coursePeriod) {
        for (CourseTime status : values()) {
            if (status.coursePeriod == coursePeriod) {
                return status.timeTo;
            }
        }
        throw new IllegalArgumentException("无效的时间段: " + coursePeriod);
    }
}
