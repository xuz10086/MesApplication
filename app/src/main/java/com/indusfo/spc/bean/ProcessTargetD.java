package com.indusfo.spc.bean;

import java.util.List;

/**
 * 工艺检测目标pojo
 *
 * @author xuz
 * @date 2019/3/8 11:05 AM
 * @param
 * @return
 */
public class ProcessTargetD extends BasePojo {

    //工艺目标id
    private Integer lProTarget;
    //工艺目标名称
    private String vcProTarget;
    //顺序号
    private Integer lOrder;
    //类型
    private String vcType;
    //工艺流程id
    private Integer lProFlow;
    //检测项目id
    private Integer lDeteProj;

    /**产品检测单id*/
    private Integer lDeteId;
    /**产品检测单值集合*/
    private List<ProductDeteValueD> productDeteValueList;

    /**检测条件*/
    private List<DeteCondD> deteCondList;

    //检测项目名称
    private String vcDeteName;
    /**仪器类型ID*/
    private Integer lInsType;
    /**仪器类型 翻译*/
    private String lInsTypeName;

    //检测分组
    private Integer lDeteGroup;
    //检测方法id
    private Integer lDeteMethod;
    //检测方法名称
    private String vcDeteMethod;
    //计量单位id
    private Integer lUnit;
    //计量单位(翻译)
    private String lUnitName;
    //计量单位(翻译)
    private String lUnitNo;
    //规格上限
    private String vcUpperLimit;
    //规格下限
    private String vcLowerLimit;
    //中心值
    private String vcCenter;
    //内控上限
    private String vcUpperIn;
    //内控下限
    private String vcLowerIn;
    //说明
    private String vcRemark;
    //数据状态
    private String lDataStateName;
    //每组样本数
    private Integer lCounts;


    public Integer getlInsType() {
        return lInsType;
    }

    public void setlInsType(Integer lInsType) {
        this.lInsType = lInsType;
    }

    public String getlInsTypeName() {
        return lInsTypeName;
    }

    public void setlInsTypeName(String lInsTypeName) {
        this.lInsTypeName = lInsTypeName;
    }

    public Integer getlDeteId() {
        return lDeteId;
    }

    public void setlDeteId(Integer lDeteId) {
        this.lDeteId = lDeteId;
    }

    public List<ProductDeteValueD> getProductDeteValueList() {
        return productDeteValueList;
    }

    public void setProductDeteValueList(List<ProductDeteValueD> productDeteValueList) {
        this.productDeteValueList = productDeteValueList;
    }

    public Integer getlProTarget() {
        return lProTarget;
    }

    public void setlProTarget(Integer lProTarget) {
        this.lProTarget = lProTarget;
    }

    public String getVcProTarget() {
        return vcProTarget;
    }

    public void setVcProTarget(String vcProTarget) {
        this.vcProTarget = vcProTarget;
    }

    public Integer getlOrder() {
        return lOrder;
    }

    public void setlOrder(Integer lOrder) {
        this.lOrder = lOrder;
    }

    public String getVcType() {
        return vcType;
    }

    public void setVcType(String vcType) {
        this.vcType = vcType;
    }

    public Integer getlProFlow() {
        return lProFlow;
    }

    public void setlProFlow(Integer lProFlow) {
        this.lProFlow = lProFlow;
    }

    public Integer getlDeteProj() {
        return lDeteProj;
    }

    public void setlDeteProj(Integer lDeteProj) {
        this.lDeteProj = lDeteProj;
    }

    public List<DeteCondD> getDeteCondList() {
        return deteCondList;
    }

    public void setDeteCondList(List<DeteCondD> deteCondList) {
        this.deteCondList = deteCondList;
    }

    public String getVcDeteName() {
        return vcDeteName;
    }

    public void setVcDeteName(String vcDeteName) {
        this.vcDeteName = vcDeteName;
    }

    public Integer getlDeteGroup() {
        return lDeteGroup;
    }

    public void setlDeteGroup(Integer lDeteGroup) {
        this.lDeteGroup = lDeteGroup;
    }

    public Integer getlDeteMethod() {
        return lDeteMethod;
    }

    public void setlDeteMethod(Integer lDeteMethod) {
        this.lDeteMethod = lDeteMethod;
    }

    public String getVcDeteMethod() {
        return vcDeteMethod;
    }

    public void setVcDeteMethod(String vcDeteMethod) {
        this.vcDeteMethod = vcDeteMethod;
    }

    public Integer getlUnit() {
        return lUnit;
    }

    public void setlUnit(Integer lUnit) {
        this.lUnit = lUnit;
    }

    public String getlUnitName() {
        return lUnitName;
    }

    public void setlUnitName(String lUnitName) {
        this.lUnitName = lUnitName;
    }

    public String getlUnitNo() {
        return lUnitNo;
    }

    public void setlUnitNo(String lUnitNo) {
        this.lUnitNo = lUnitNo;
    }

    public String getVcUpperLimit() {
        return vcUpperLimit;
    }

    public void setVcUpperLimit(String vcUpperLimit) {
        this.vcUpperLimit = vcUpperLimit;
    }

    public String getVcLowerLimit() {
        return vcLowerLimit;
    }

    public void setVcLowerLimit(String vcLowerLimit) {
        this.vcLowerLimit = vcLowerLimit;
    }

    public String getVcCenter() {
        return vcCenter;
    }

    public void setVcCenter(String vcCenter) {
        this.vcCenter = vcCenter;
    }

    public String getVcUpperIn() {
        return vcUpperIn;
    }

    public void setVcUpperIn(String vcUpperIn) {
        this.vcUpperIn = vcUpperIn;
    }

    public String getVcLowerIn() {
        return vcLowerIn;
    }

    public void setVcLowerIn(String vcLowerIn) {
        this.vcLowerIn = vcLowerIn;
    }

    public String getVcRemark() {
        return vcRemark;
    }

    public void setVcRemark(String vcRemark) {
        this.vcRemark = vcRemark;
    }

    public String getlDataStateName() {
        return lDataStateName;
    }

    public void setlDataStateName(String lDataStateName) {
        this.lDataStateName = lDataStateName;
    }

    public Integer getlCounts() {
        return lCounts;
    }

    public void setlCounts(Integer lCounts) {
        this.lCounts = lCounts;
    }


    @Override
    public String toString() {
        return "ProcessTargetD{" +
                "lProTarget=" + lProTarget +
                ", vcProTarget='" + vcProTarget + '\'' +
                ", lOrder=" + lOrder +
                ", vcType='" + vcType + '\'' +
                ", lProFlow=" + lProFlow +
                ", lDeteProj=" + lDeteProj +
                ", lDeteId=" + lDeteId +
                ", productDeteValueList=" + productDeteValueList +
                ", deteCondList=" + deteCondList +
                ", vcDeteName='" + vcDeteName + '\'' +
                ", lInsType=" + lInsType +
                ", lInsTypeName='" + lInsTypeName + '\'' +
                ", lDeteGroup=" + lDeteGroup +
                ", lDeteMethod=" + lDeteMethod +
                ", vcDeteMethod='" + vcDeteMethod + '\'' +
                ", lUnit=" + lUnit +
                ", lUnitName='" + lUnitName + '\'' +
                ", lUnitNo='" + lUnitNo + '\'' +
                ", vcUpperLimit='" + vcUpperLimit + '\'' +
                ", vcLowerLimit='" + vcLowerLimit + '\'' +
                ", vcCenter='" + vcCenter + '\'' +
                ", vcUpperIn='" + vcUpperIn + '\'' +
                ", vcLowerIn='" + vcLowerIn + '\'' +
                ", vcRemark='" + vcRemark + '\'' +
                ", lDataStateName='" + lDataStateName + '\'' +
                ", lCounts=" + lCounts +
                '}';
    }
}
