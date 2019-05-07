package com.indusfo.spc.bean;

/**
 * 产品测试单值表pojo
 *
 * @author xuz
 * @date 2019/3/8 11:05 AM
 * @param
 * @return
 */
public class ProductDeteValueD {
    //检测单值ID
    private Integer lDeteValueId;
    //检测单ID
    private Integer lDeteId;
    //工艺目标ID
    private Integer lProTarget;
    //检测值
    private String vcValue;
    //测试人
    private  Integer lUserId;
    /**测试人名字*/
    private String lUserIdName;
    //测试时间
    private String dCreateTime;
    //说明
    private String vcRemark;
    //数据状态
    private Integer lDataState;


    /**取样数*/
    private Integer sampleNum;
    /**最大值*/
    private Double maxValue;
    /**最小值*/
    private Double minValue;

    //检测项目id(关联字段）
    private Integer lDeteProj;
    //检测项目名称（关联字段）
    private  String vcDeteName;

    public Integer getlDeteValueId() {
        return lDeteValueId;
    }

    public void setlDeteValueId(Integer lDeteValueId) {
        this.lDeteValueId = lDeteValueId;
    }

    public Integer getlDeteId() {
        return lDeteId;
    }

    public void setlDeteId(Integer lDeteId) {
        this.lDeteId = lDeteId;
    }

    public Integer getlProTarget() {
        return lProTarget;
    }

    public void setlProTarget(Integer lProTarget) {
        this.lProTarget = lProTarget;
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

    public String getlUserIdName() {
        return lUserIdName;
    }

    public void setlUserIdName(String lUserIdName) {
        this.lUserIdName = lUserIdName;
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

    public Integer getSampleNum() {
        return sampleNum;
    }

    public void setSampleNum(Integer sampleNum) {
        this.sampleNum = sampleNum;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
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

    @Override
    public String toString() {
        return "ProductDeteValue{" +
                "lDeteValueId=" + lDeteValueId +
                ", lDeteId=" + lDeteId +
                ", lProTarget=" + lProTarget +
                ", vcValue='" + vcValue + '\'' +
                ", lUserId=" + lUserId +
                ", lUserIdName='" + lUserIdName + '\'' +
                ", dCreateTime='" + dCreateTime + '\'' +
                ", vcRemark='" + vcRemark + '\'' +
                ", lDataState=" + lDataState +
                ", sampleNum=" + sampleNum +
                ", maxValue=" + maxValue +
                ", minValue=" + minValue +
                ", lDeteProj=" + lDeteProj +
                ", vcDeteName='" + vcDeteName + '\'' +
                '}';
    }
}
