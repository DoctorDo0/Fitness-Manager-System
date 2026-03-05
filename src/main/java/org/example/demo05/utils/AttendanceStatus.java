package org.example.demo05.utils;

import lombok.Getter;

@Getter
public enum AttendanceStatus {
    ATTEND(0, "已签到"),
    ABSENT(1, "旷课"),
    LATE(2, "迟到"),
    LEAVE(3, "请假");

    private final int code;      // 数据库存的值
    private final String desc;   // 显示的文字

    AttendanceStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    // 根据code获取desc
    public static String getDescFromCode(int code) {
        for (AttendanceStatus status : values()) {
            if (status.code == code) {
                return status.desc;
            }
        }
        throw new IllegalArgumentException("无效的状态码: " + code);
    }
}
// 数据库中存 code: 0,1,2,3