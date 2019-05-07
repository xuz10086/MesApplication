package com.indusfo.spc.bean;

import java.io.Serializable;

/**
 * 封装服务器端返回结果
 *
 * @author xuz
 * @date 2019/1/11 2:15 PM
 * @param
 * @return
 */
public class RResult implements Serializable {

    private static final long serialVersionUID = -5562984834736752966L;
    private String code;
    private String msg;
    private String data;
    private Integer lCounts;
    private boolean ok;
    private String cookie;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getlCounts() {
        return lCounts;
    }

    public void setlCounts(Integer lCounts) {
        this.lCounts = lCounts;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    @Override
    public String toString() {
        return "RResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data='" + data + '\'' +
                ", lCounts=" + lCounts +
                ", ok=" + ok +
                ", cookie='" + cookie + '\'' +
                '}';
    }
}
