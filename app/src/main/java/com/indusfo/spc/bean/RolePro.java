package com.indusfo.spc.bean;

import java.io.Serializable;
import java.util.List;

public class RolePro implements Serializable {

    private User user;
    private List<Pro> proList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Pro> getProList() {
        return proList;
    }

    public void setProList(List<Pro> proList) {
        this.proList = proList;
    }

    @Override
    public String toString() {
        return "RolePro{" +
                "user=" + user +
                ", proList=" + proList +
                '}';
    }
}
