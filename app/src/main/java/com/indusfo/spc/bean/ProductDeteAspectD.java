package com.indusfo.spc.bean;

import java.io.Serializable;

/**
 * 外观检测值
 *
 * @author xuz
 * @date 2019/3/8 11:05 AM
 * @param
 * @return
 */
public class ProductDeteAspectD implements Serializable {
    private static final long serialVersionUID = -1954298886240146460L;

    private Integer lDeteId; //产品检测单id
    private Integer lDeteProj; //检测项目id
    private String vcDeteName; //检测项目名字(翻译)
    private Integer lSwatch; //样本数
    private String vcValue; //检测值
    private Integer lUserId; //用户id
    private String vcUserName; //用户名字(翻译)
    private String dCreateTime; //创建时间
    private String vcRemark; //说明
    private Integer lDataState; //状态
    private String percentage; //不合格比例

    public Integer getlDeteId() {
        return lDeteId;
    }

    public void setlDeteId(Integer lDeteId) {
        this.lDeteId = lDeteId;
    }

    public Integer getlDeteProj() {
        return lDeteProj;
    }

    public void setlDeteProj(Integer lDeteProj) {
        this.lDeteProj = lDeteProj;
    }

    public String getVcDeteName() {
        return vcDeteName;
    }

    public void setVcDeteName(String vcDeteName) {
        this.vcDeteName = vcDeteName;
    }

    public Integer getlSwatch() {
        return lSwatch;
    }

    public void setlSwatch(Integer lSwatch) {
        this.lSwatch = lSwatch;
    }

    public String getVcValue() {
        return vcValue;
    }

    public void setVcValue(String vcValue) {
        this.vcValue = vcValue;
    }

    public Integer getlUserId() {
        return lUserId;
    }

    public void setlUserId(Integer lUserId) {
        this.lUserId = lUserId;
    }

    public String getVcUserName() {
        return vcUserName;
    }

    public void setVcUserName(String vcUserName) {
        this.vcUserName = vcUserName;
    }

    public String getdCreateTime() {
        return dCreateTime;
    }

    public void setdCreateTime(String dCreateTime) {
        this.dCreateTime = dCreateTime;
    }

    public String getVcRemark() {
        return vcRemark;
    }

    public void setVcRemark(String vcRemark) {
        this.vcRemark = vcRemark;
    }

    public Integer getlDataState() {
        return lDataState;
    }

    public void setlDataState(Integer lDataState) {
        this.lDataState = lDataState;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "ProductDeteAspectD{" +
                "lDeteId=" + lDeteId +
                ", lDeteProj=" + lDeteProj +
                ", vcDeteName='" + vcDeteName + '\'' +
                ", lSwatch=" + lSwatch +
                ", vcValue='" + vcValue + '\'' +
                ", lUserId=" + lUserId +
                ", vcUserName='" + vcUserName + '\'' +
                ", dCreateTime='" + dCreateTime + '\'' +
                ", vcRemark='" + vcRemark + '\'' +
                ", lDataState=" + lDataState +
                ", percentage='" + percentage + '\'' +
                '}';
    }
}
