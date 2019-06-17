package com.indusfo.spc.bean;

import java.io.Serializable;

/**
 * 工序
 *
 * @author xuz
 * @date 2019/6/5 3:01 PM
 * @param
 * @return
 */
public class Pro implements Serializable {
    // 工序id
    private Integer lProId;
    // 工序名称
    private String vcProName;

    public Integer getlProId() {
        return lProId;
    }

    public void setlProId(Integer lProId) {
        this.lProId = lProId;
    }

    public String getVcProName() {
        return vcProName;
    }

    public void setVcProName(String vcProName) {
        this.vcProName = vcProName;
    }

    @Override
    public String toString() {
        return "Pro{" +
                "lProId=" + lProId +
                ", vcProName='" + vcProName + '\'' +
                '}';
    }
}
