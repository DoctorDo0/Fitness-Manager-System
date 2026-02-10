package org.example.demo05.common;

public class Global {
    //同一线程之间存储的数据的容器
    private static final ThreadLocal<String> th = new ThreadLocal<>();

    //返回当前登录用户
    public static String currentUser() {
        return th.get();
    }

    //存储当前登录用户
    public static void setCurrentUser(String username) {
        th.set(username);
    }
}
