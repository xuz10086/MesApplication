package com.indusfo.spc.bean;

/**
 * Spinner条目的实体类
 *  * 重写toString方法，默认显示条目toString
 *
 * @author xuz
 * @date 2019/6/1 9:06 AM
 * @param
 * @return
 */
public class SpinnerIdAndValue {
    private Integer id;
    private String value;

    public SpinnerIdAndValue(Integer id, String value) {
        this.id = id;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
