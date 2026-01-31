package org.example.demo05.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JsonResp {
    private int code;
    private boolean success;
    private String message;
    private Object data;

    public static JsonResp success() {
        JsonResp resp = new JsonResp();
        resp.code = 200;
        resp.success = true;
        resp.message = "success";
        resp.data = null;
        return resp;
    }

    public static JsonResp success(Object data) {
        JsonResp resp = JsonResp.success();
        resp.data = data;
        return resp;
    }

    public static JsonResp error() {
        JsonResp resp = new JsonResp();
        resp.code = 500;
        resp.success = false;
        resp.message = "error";
        resp.data = null;
        return resp;
    }

    public static JsonResp error(int code, String message) {
        JsonResp resp = JsonResp.error();
        resp.code = code;
        resp.message = message;
        return resp;
    }
}
