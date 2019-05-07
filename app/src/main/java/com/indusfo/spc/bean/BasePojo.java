package com.indusfo.spc.bean;

import java.io.Serializable;

/***
 * pojo基类：用于扩展分页功能滴，你离分页只差一个继承哦
 *
 * @author xuz
 * @date 2018/11/22 2:18 PM
 */

public class BasePojo implements Serializable {

    private static final long serialVersionUID = 5762265332554874563L;
    private  Integer pageindex;
    private  Integer pagesize;
    private Integer lN;
    /**
     * 高级查询条件
     */
    private String strwhere;
    /**
     * 模糊查询接受字段
     */
    private String dim;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getPageindex() {
        return pageindex;
    }

    public void setPageindex(Integer pageindex) {
        this.pageindex = pageindex;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public Integer getlN() {
        return lN;
    }

    public void setlN(Integer lN) {
        this.lN = lN;
    }

    public String getStrwhere() {
        return strwhere;
    }

    public void setStrwhere(String strwhere) {
        this.strwhere = strwhere;
    }

    public String getDim() {
        return dim;
    }

    public void setDim(String dim) {
        this.dim = dim;
    }
}