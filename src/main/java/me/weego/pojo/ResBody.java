package me.weego.pojo;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author tcl
 */
public class ResBody<T> {
    private int code = 0;
    private String msg = "";
    private T data;

    private ResBody(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ResBody<T> returnSuccess(T data) {
        return new ResBody<T>(0, "success", data);
    }

    public static <T> ResBody<T> returnFail(int code, String msg) {
        return new ResBody<T>(code, msg, null);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("code", code)
                .append("msg", msg)
                .append("data", data)
                .toString();
    }
}
