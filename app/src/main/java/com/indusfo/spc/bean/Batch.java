package com.indusfo.spc.bean;

/**
 * 批次表
 *
 * @author xuz
 * @date 2019/3/8 11:05 AM
 * @param
 * @return
 */
public class Batch extends BasePojo{

    /**
     * 
     */
    private static final long serialVersionUID = 6651470197225746539L;
    // 批次ID
    private Integer lBatchId;
    // 批次编号
    private String vcBatchCode;
    // 用户ID
    private Integer lUserId;
    // 用户名(翻译)
    private String vcUserName;
    // 班次
    private Integer lClasses;
    // 班次（翻译）
    private String vcClasses;
    // 时间
    private String dDatetime;
    // 报工ID
    private Integer lWorkFinish;
    // 设备ID
    private Integer lEquipment;
    // 机台号
    private String vcEquipment;
    // 批次状态
    private Integer lBatchState;
    // 批次状态数据字典翻译
    private String lBatchStateName;
    // 锁定标志
    private Integer lLocked;
    // 关联批次编号
    private String vcBatchLink;
    // 说明
    private String vcRemark;
    // 数据状态
    private Integer lDataState;
    // 数据状态(数据字典翻译)
    private String lDataStateName;
    // 生产计划单id
    private Integer lPlanId;
    // 计划数量
    private String vcPlanNum;
    // 排产单ID
    private Integer lPlanWork;
    // 排产单号
    private String vcPlanWork;
    // 工序id
    private Integer lProId;
    // 工序名称
    private String vcProName;
    // 是否自动生成检测单
    private Integer lAutoDete;
    // 是否必须检测后完工
    private Integer lDeteFinish;
    // 产品id
    private Integer lProduct;
    // 产品名称
    private String vcProductName;
    // 计量单位
    private Integer lUnit;
    // 规格型号
    private String vcModel;
    // 上道工序完工数量
    private String vcLastNum;
    // 顺序号
    private Integer lOrder;
    // 工艺版本
    private Integer lProVer;
    // 计划生产数量
    private String vcNum;
    // 完工数量
    private String vcFinishNum;
    // 产品检测单id
    private Integer lDeteId;
    // 工艺流程id
    private Integer lProFlow;
    //完成的比例
    private String proportion;

    public Integer getlBatchId() {
        return lBatchId;
    }

    public void setlBatchId(Integer lBatchId) {
        this.lBatchId = lBatchId;
    }

    public String getVcBatchCode() {
        return vcBatchCode;
    }

    public void setVcBatchCode(String vcBatchCode) {
        this.vcBatchCode = vcBatchCode;
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

    public Integer getlClasses() {
        return lClasses;
    }

    public void setlClasses(Integer lClasses) {
        this.lClasses = lClasses;
    }

    public String getVcClasses() {
        return vcClasses;
    }

    public void setVcClasses(String vcClasses) {
        this.vcClasses = vcClasses;
    }

    public String getdDatetime() {
        return dDatetime;
    }

    public void setdDatetime(String dDatetime) {
        this.dDatetime = dDatetime;
    }

    public Integer getlWorkFinish() {
        return lWorkFinish;
    }

    public void setlWorkFinish(Integer lWorkFinish) {
        this.lWorkFinish = lWorkFinish;
    }

    public Integer getlEquipment() {
        return lEquipment;
    }

    public void setlEquipment(Integer lEquipment) {
        this.lEquipment = lEquipment;
    }

    public String getVcEquipment() {
        return vcEquipment;
    }

    public void setVcEquipment(String vcEquipment) {
        this.vcEquipment = vcEquipment;
    }

    public Integer getlBatchState() {
        return lBatchState;
    }

    public void setlBatchState(Integer lBatchState) {
        this.lBatchState = lBatchState;
    }

    public String getlBatchStateName() {
        return lBatchStateName;
    }

    public void setlBatchStateName(String lBatchStateName) {
        this.lBatchStateName = lBatchStateName;
    }

    public Integer getlLocked() {
        return lLocked;
    }

    public void setlLocked(Integer lLocked) {
        this.lLocked = lLocked;
    }

    public String getVcBatchLink() {
        return vcBatchLink;
    }

    public void setVcBatchLink(String vcBatchLink) {
        this.vcBatchLink = vcBatchLink;
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

    public String getlDataStateName() {
        return lDataStateName;
    }

    public void setlDataStateName(String lDataStateName) {
        this.lDataStateName = lDataStateName;
    }

    public Integer getlPlanId() {
        return lPlanId;
    }

    public void setlPlanId(Integer lPlanId) {
        this.lPlanId = lPlanId;
    }

    public String getVcPlanNum() {
        return vcPlanNum;
    }

    public void setVcPlanNum(String vcPlanNum) {
        this.vcPlanNum = vcPlanNum;
    }

    public Integer getlPlanWork() {
        return lPlanWork;
    }

    public void setlPlanWork(Integer lPlanWork) {
        this.lPlanWork = lPlanWork;
    }

    public String getVcPlanWork() {
        return vcPlanWork;
    }

    public void setVcPlanWork(String vcPlanWork) {
        this.vcPlanWork = vcPlanWork;
    }

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

    public Integer getlAutoDete() {
        return lAutoDete;
    }

    public void setlAutoDete(Integer lAutoDete) {
        this.lAutoDete = lAutoDete;
    }

    public Integer getlDeteFinish() {
        return lDeteFinish;
    }

    public void setlDeteFinish(Integer lDeteFinish) {
        this.lDeteFinish = lDeteFinish;
    }

    public Integer getlProduct() {
        return lProduct;
    }

    public void setlProduct(Integer lProduct) {
        this.lProduct = lProduct;
    }

    public String getVcProductName() {
        return vcProductName;
    }

    public void setVcProductName(String vcProductName) {
        this.vcProductName = vcProductName;
    }

    public Integer getlUnit() {
        return lUnit;
    }

    public void setlUnit(Integer lUnit) {
        this.lUnit = lUnit;
    }

    public String getVcModel() {
        return vcModel;
    }

    public void setVcModel(String vcModel) {
        this.vcModel = vcModel;
    }

    public String getVcLastNum() {
        return vcLastNum;
    }

    public void setVcLastNum(String vcLastNum) {
        this.vcLastNum = vcLastNum;
    }

    public Integer getlOrder() {
        return lOrder;
    }

    public void setlOrder(Integer lOrder) {
        this.lOrder = lOrder;
    }

    public Integer getlProVer() {
        return lProVer;
    }

    public void setlProVer(Integer lProVer) {
        this.lProVer = lProVer;
    }

    public String getVcNum() {
        return vcNum;
    }

    public void setVcNum(String vcNum) {
        this.vcNum = vcNum;
    }

    public String getVcFinishNum() {
        return vcFinishNum;
    }

    public void setVcFinishNum(String vcFinishNum) {
        this.vcFinishNum = vcFinishNum;
    }

    public Integer getlDeteId() {
        return lDeteId;
    }

    public void setlDeteId(Integer lDeteId) {
        this.lDeteId = lDeteId;
    }

    public Integer getlProFlow() {
        return lProFlow;
    }

    public void setlProFlow(Integer lProFlow) {
        this.lProFlow = lProFlow;
    }

    public String getProportion() {
        return proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion;
    }

    @Override
    public String toString() {
        return "Batch{" +
                "lBatchId=" + lBatchId +
                ", vcBatchCode='" + vcBatchCode + '\'' +
                ", lUserId=" + lUserId +
                ", vcUserName='" + vcUserName + '\'' +
                ", lClasses=" + lClasses +
                ", vcClasses='" + vcClasses + '\'' +
                ", dDatetime='" + dDatetime + '\'' +
                ", lWorkFinish=" + lWorkFinish +
                ", lEquipment=" + lEquipment +
                ", vcEquipment='" + vcEquipment + '\'' +
                ", lBatchState=" + lBatchState +
                ", lBatchStateName='" + lBatchStateName + '\'' +
                ", lLocked=" + lLocked +
                ", vcBatchLink='" + vcBatchLink + '\'' +
                ", vcRemark='" + vcRemark + '\'' +
                ", lDataState=" + lDataState +
                ", lDataStateName='" + lDataStateName + '\'' +
                ", lPlanId=" + lPlanId +
                ", vcPlanNum='" + vcPlanNum + '\'' +
                ", lPlanWork=" + lPlanWork +
                ", vcPlanWork='" + vcPlanWork + '\'' +
                ", lProId=" + lProId +
                ", vcProName='" + vcProName + '\'' +
                ", lAutoDete=" + lAutoDete +
                ", lDeteFinish=" + lDeteFinish +
                ", lProduct=" + lProduct +
                ", vcProductName='" + vcProductName + '\'' +
                ", lUnit=" + lUnit +
                ", vcModel='" + vcModel + '\'' +
                ", vcLastNum='" + vcLastNum + '\'' +
                ", lOrder=" + lOrder +
                ", lProVer=" + lProVer +
                ", vcNum='" + vcNum + '\'' +
                ", vcFinishNum='" + vcFinishNum + '\'' +
                ", lDeteId=" + lDeteId +
                ", lProFlow=" + lProFlow +
                ", proportion='" + proportion + '\'' +
                '}';
    }
}
